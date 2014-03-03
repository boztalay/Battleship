#ifndef BATTLESHIP_GAME_HEADER
#define BATTLESHIP_GAME_HEADER

#include <vector>
#include "player.h"

class BattleshipGame {
private:
	std::vector<Player> players;	

public:
	BattleshipGame(void);

	BattleshipGame& configure(void);
	BattleshipGame& fieldSize(int);
	BattleshipGame& randomShipPlacement(void);
	BattleshipGame& manualShipPlacement(void);

	int getFieldSize(void);
};

#endif
