package main

import (
	"fmt"
	"math/rand"
	"net"
	"os"
	"strconv"
	"strings"
	"time"
)

type client struct {
	socket   net.Conn
	data     chan []byte
	lastSent int64
	wait     int64
}

type message struct {
	board  [64]int
	turn   int
	round  int
	p1Time float64
	p2Time float64
}

type game struct {
	playerNum int
	enemyNum  int
	time      float64
	ack       bool
	gameOver  bool
	message   message
}

func (client *client) receive(myGame *game) {
	for {
		mess := make([]byte, 4096)
		length, err := client.socket.Read(mess)
		if err != nil {
			client.socket.Close()
			break
		}
		if length < 0 {
			continue
		}

		//We got some incomming data
		fmt.Println("reciving message...")
		data := strings.Split(string(mess), "\n")

		//First message
		if !myGame.ack {
			data := strings.Split(data[0], " ")
			pNum, err := strconv.ParseInt(data[0], 10, 32)
			myGame.playerNum = int(pNum)
			if err != nil {
				fmt.Printf("Invalid player number recieved: '%s'\n", data[0])
				client.socket.Close()
				break
			}
			myGame.time, err = strconv.ParseFloat(data[1], 32)
			if err != nil {
				fmt.Printf("Invalid game time recieved: '%s'\n", data[1])
				client.socket.Close()
				break
			}
			myGame.ack = true
			continue
		}

		if data[0] == "-999" {
			myGame.gameOver = true
			return //The game is over, stop reading stuff
		}

		// update the game state
		turn, err := strconv.ParseInt(data[0], 10, 32)
		if err != nil {
			fmt.Printf("Game state not updated, invalid turn recieved '%s', Error: %s\n", data[0], err.Error())
			continue
		}
		round, err := strconv.ParseInt(data[1], 10, 32)
		if err != nil {
			fmt.Printf("Game state not updated, invalid round recieved '%s', Error: %s\n", data[1], err.Error())
			continue
		}
		p1t, err := strconv.ParseFloat(data[2], 32)
		if err != nil {
			fmt.Printf("Game state not updated, invalid p1Time recieved: '%s', Error: %s\n", data[2], err.Error())
			continue
		}
		p2t, err := strconv.ParseFloat(data[3], 32)
		if err != nil {
			fmt.Printf("Game state not updated, invalid p2Time recieved: '%s', Error: %s\n", data[3], err.Error())
			continue
		}

		//convert the board
		board := [64]int{}
		for i, s := range data[4:68] {
			d, err := strconv.ParseInt(s, 10, 8)
			if err != nil {
				fmt.Printf("Game state not updated, invalid board recieved: '%+v', Error: %s\n", data[4:68], err.Error())
			}
			board[i] = int(d)
		}

		myGame.message = message{
			board:  board,
			turn:   int(turn),
			round:  int(round),
			p1Time: p1t,
			p2Time: p2t,
		}
	}
}

func (client *client) sendMove(row, col int) {
	now := time.Now().UnixNano()
	if client.lastSent == 0 {
		client.lastSent = now - client.wait - 1 //Set the first lastSent time
	}
	if now-client.lastSent < client.wait { //prevent spamming the server which can cause issues
		return
	}
	client.lastSent = now
	move := fmt.Sprintf("%d\n%d", row, col)
	client.socket.Write([]byte(move)) //Send the move
	client.socket.Write([]byte("\n")) //Finish the message
	fmt.Printf("\nMove: (%d, %d)\n", row, col)
}

func (client *client) requestUpdate() {
	client.socket.Write([]byte("\n")) //Request a new simple update
	fmt.Println("Requesting update from the server")
}

func main() {
	if len(os.Args) < 3 {
		fmt.Printf("Please specify both the address of the server and the player number\n")
		return
	}

	fmt.Printf("connecting to the Reversi server @ %s...\n", os.Args[1])
	port, err := strconv.Atoi(os.Args[2])
	if err != nil {
		fmt.Printf("Player number must be 1 or 2: %s", err.Error())
	}
	connection, err := net.Dial("tcp", fmt.Sprintf("%s:%d", os.Args[1], 3333+port))
	if err != nil {
		fmt.Printf("There was an error connecting to the server: %s\n", err.Error())
		return
	}

	sec := 1000000000.0 //a second in nano seconds
	client := &client{socket: connection, wait: int64(0.1 * sec)}
	myGame := game{}
	go client.receive(&myGame) //start a go routine to recieve server input

	timeout := int64(5 * sec)
	for {
		if time.Now().UnixNano()-client.lastSent > timeout && client.lastSent != 0 {
			client.requestUpdate() //need to jog the server
		}
		if myGame.gameOver {
			fmt.Println("Game Over")
			return
		}

		// send a move to the server if it's my turn
		if myGame.message.turn == myGame.playerNum && myGame.ack && !myGame.gameOver {
			row := rand.Intn(8)
			col := rand.Intn(8)
			client.sendMove(row, col)
		}
	}
}
