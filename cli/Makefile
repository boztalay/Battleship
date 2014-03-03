FLAGS = -O3 -Wall -Wextra -pedantic -Wvla -std=c++11 -stdlib=libc++
ENGINE_PATH = ../engine

EXE_NAME = battleship

all: battleship.cpp battleship_game.o player.o
	clang++ $(FLAGS) -I $(ENGINE_PATH) battleship.cpp battleship_game.o player.o -o $(EXE_NAME)

battleship_game.o:
	clang++ $(FLAGS) -c $(ENGINE_PATH)/battleship_game.cpp

player.o:
	clang++ $(FLAGS) -c $(ENGINE_PATH)/player.cpp

clean:
	rm -f *.o $(EXE_NAME)
