#ifndef PLAYER_HEADER
#define PLAYER_HEADER

enum class FieldSquare {
	EMPTY = 0,
	MISS = 1,
	SHIP = 2,
	HIT = 3
};

enum class ShipPlacementResult {
	VALID = 0,
	INVALID = 1
};

enum class ShipOrientation {
	HORIZONTAL = 0,
	VERTICAL = 1
};

class Player {
private:

public:	
	Player(void);

	FieldSquare getSquareInFieldAt(int, int);

	ShipPlacementResult placeShipAt(int, int, ShipOrientation);
	FieldSquare fireMissleAt(int, int);
};

#endif
