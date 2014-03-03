

FLAGS = -Wall -Wextra -pedantic -Wvla -std=c++11 -stdlib=libc++
ENGINE_PATH = ../engine

EXE_NAME = battleship

all: battleship.cpp battleship_game.o
	clang++ $(FLAGS) -I $(ENGINE_PATH) battleship.cpp battleship_game.o -o $(EXE_NAME)

battleship_game.o:
	clang++ $(FLAGS) -c $(ENGINE_PATH)/battleship_game.cpp

clean:
	rm -f *.o $(EXE_NAME)
