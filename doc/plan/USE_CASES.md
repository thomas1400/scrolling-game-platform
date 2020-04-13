# USE_CASES.md
## Model Use Cases
### USE CASE 1 - Model
A player collides with an enemy on the side or from below (enemy above player). The player's life status should change to dead, while nothing should happen to the enemy. Both updated entities are returned to the game engine.

### USE CASE 2 - Model
A player collides with an enemy from above (player above enemy). The player should bounce, and the enemy should become stunned. Both updated entities are returned to the game engine.

### USE CASE 3 - Model
A player collides with a stunned enemy from above. The player should bounce, and the enemy should be re-stunned (the timer on the stun should be reset) or killed depending on the enemy's type. Both updated entities are returned to the game engine.

### USE CASE 4 - Model
A player collides with a stunned enemy (projectile type) from the side. Nothing should happen to the player, but a projectile entity is created in the place of the enemy and the enemy should be marked as dead. Both updated entities are returned to the game engine.

### USE CASE 5 - Model
An enemy collides with another enemy. Nothing happens to either. Both updated entities are returned to the game engine.

### USE CASE 6 - Model
An enemy collides with a projectile. The enemy dies, and the projectile loses a hit (if it has a number of hits). Both updated entities are returned to the game engine.

### USE CASE 7 - Model
The user presses the space bar, and the game engine calls `jump()` in `Player`. The player gains y-velocity, and starts moving upward. After some time, the player begins to have negative y velocity until it hits terrain.

### USE CASE 8 - Model
The player jumps on top of a stunnable enemy. The enemy becomes stunned, and after a certain amount of time with no action, the enemy becomes unstunned and resumes whatever action or movement it was doing previously.

## Game Engine Use Cases
### USE CASE 1 - Game Engine
The user presses the right arrow key. The InputManager recognizes that the right arrow key has been pressed, and calls a method to move the main charcater to the right. The CameraManager then senses that the main character is now not in the center of the screen. The CameraManager adjusts the coordinates of the character that is relative to the screen so that the main character is located in the center. The camera then adjusts the screen-related-coordinates of all entities that are currently located on the screen so that they are shifted by the amount that it took to return the main character to the center of the screen. This includes the addition and removal of entities as the screen moves. 

### USE CASE 2 - Game Engine
The collision manager detects a collision between two entities. The collisionManager creates two Collision objects, and passes those objects to the entities that collided. The CollisionManager gets the return Entity from the Collision and determines if this is a new Entity and if any of the existing entities have died.

### USE CASE 3 - Game Engine
The EntityManager detcts that an entity has died. The EntityManager removes the entity from the EntityList and tells the Level controller to remove that Entity from the display.


### USE CASE 4 - Game Engine
The user selects the pause button while playing a basicLevel. The ScreenController creates a screen the displays that the game is paused and has the options to resume or exit the game. The timeline within LevelLoop is temporarily stopped. 

### USE CASE 5 - Game Engine
A collision causes an Entity to die and a new entity to be created in place of that Entity. The EntityManager receives an Entity from the CollisionManager. This Entity is not currently in the EntityList. The Entity is added to the EntityList. The EntityManager then checks to see if any of the current Entities have died. The EntityManager sees that one of the Entities has died, and removes this Entity from the List. 

### USE CASE 6 - Game Engine
The main character jumps on top of a tube. The CollisionManager detects a collision between the character and the tube from the top, and tells the LevelController that we are entering a tube. The LevelController creates a new Level that dispays an underwater background and new physics components for Entity. 

### USE CASE 7 - Game Engine
The main character jumps on top of an enemy. The CollisionManager detects the collision,  and executes respective methods. The EntityManager detects that the state of one of the Entities has been changed to dead, but no new Entity is created in place of this Entity. This Entity is removed from the Entitylist, and the EntityManager tells the LevelController that it needs to be removed from the display.

### USE CASE 8 - Game Engine
The user presses the bar. The InputManager detects that the space bar has been pressed, and tells the main character to that the character needs to jump. The Entity then uses its specified Physics component to change position. 

## View Use Cases
### USE CASE 1 - View
The user selects a user profile in the UserSelect screen. Their selection is communicated to the backend, which loads in the appropriate user profile from a file. Screens in the menu now  display user-specific information, like lives remaining and levels unlocked.

### USE CASE 2 - View
The user exits the game. As they do, their progress, lives remaining, and score are saved to a user file, saved automatically to be loaded the next time the user opens the game.

### USE CASE 3 - View
The user selects a basicLevel in the LevelSelect screen and presses "Begin". A loading screen (SplashScreen) displays, showing the basicLevel to be loaded and the user's remaining lives. The basicLevel loads, and the user is shown the GameScreen. They press the left and right arrow keys to see their character move back and forth. Then, they press 'Quit', and are taken back to the main menu, without their progress in the basicLevel being saved.

### USE CASE 4 - View
The user opens the game and tries to select a user profile, but the user's data file was corrupted, and can't be loaded. An error message pops up informing the user of the problem and suggesting steps to fix it, like deleting the user file. When the user clicks "Okay", dismissing the error message, the application closes.

### USE CASE 5 - View
The user selects their user profile in the UserSelect screen, then presses "BEGIN" to move to the main menu. They click "Help", and navigate to the Help screen, where the game's controls and helpful tips are displayed.

### USE CASE 6 - View
The user completes a basicLevel, and the basicLevel progress bar beneath the LevelSelect page updates to reflect the progress, moving one bar closer to the end.

### USE CASE 7 - View
The user dies while playing the game, and a death screen fades in, displaying an animation that shows the user losing a life and displaying their remaining lives. The death screen fades out, and the basicLevel loading screen fades in. The basicLevel restarts.

### USE CASE 8 - View
The user successfully completes a basicLevel, and the basicLevel completion screen fades in, showing a victory animation and the unlocking of the next basicLevel. The user is taken back to the LevelSelect page, with the new basicLevel unlocked for selection.

## Game Controller Use Cases
### USE CASE 1 - Game Controller
The application is launched and the screen controller launches the visualizer. A welcome screen is displayed.

### USE CASE 2 - Game Controller
A basicLevel is read in, and a list of entities is created with the correct attributes and basicLevel relative position.

### USE CASE 3 - Game Controller
If the user pauses the game through the visualizer, the game controller pauses the basicLevel loop. It remains paused until the user resumes the game, in which the game controller turns on the basicLevel loop.

### USE CASE 4 - Game Controller
The user selects a new basicLevel to load. The basicLevel is read from the selected XML file, and a new basicLevel loop is created with the new list of entities. 

### USE CASE 5 - Game Controller
The user adds a second player. The new player has a different set of movement keys (which will be loaded from a properties file), and the new player is added to the list of entities in the game engine.

### USE CASE 6 - Game Controller
An invalid basicLevel file is selected. The game controller displays an error message that the basicLevel cannot be played, and prompts for the user to selected a different basicLevel.

### USE CASE 7 - Game Controller
The user reaches the end of a basicLevel, and the basicLevel loop finishes, and tells the game controller that the basicLevel is over. The game controller determines the next from from the game XML file, and then loads the next basicLevel and passes the list of entities to the new basicLevel loop.

### USE CASE 8 - Game Controller
The player looses a life. The number of lives decreases by one, and the screen controller changes the visualizer to show the 'death' screen. If the user has remaining lives, the current basicLevel is reloaded from the beginning. If the user is out of lives, the screen controller changes the visualiser to a game over screen.