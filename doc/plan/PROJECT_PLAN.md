# PROJECT_PLAN.md
## SPRINT 1 - Basic 1
We write the ScreenController, which lets you navigate between screens, home screen lets you go to the basicLevel selector, the basic basicLevel selector lets you pick a basicLevel, which opens the basicLevel. 

We have basic functionality, including walking, jumping, losing a life when falling, and completing a basicLevel. Develop very basic collision functionality where all collisions stop the player.

Create basic key shortcuts (cheat codes) for testing.

Developing how the files are going to be written and read in to create different levels and users.

The camera should move as the character does.

## SPRINT 2 - Basic 2
Add in enemies, projectiles, powerups, and improve difficulty of levels.

Adding in the details of the collision manager- included the properties files and methods associated with specific collisions

Add in the ability to load and save different users, where different users have different game stats (like loading new "games" and switching between human players' progress).

## SPRINT 3 - Complete

Add the basicLevel builder and special levels (like boss fights or vertically scrolling levels).

Add more interesting enemies, like ghosts.

Different themes (attempt making an underwater mario scene with different physics).

## Responsibilities

### Primary Responsibilities

#### dmm107 (Dana): Model
* Work on getting basic Entity classes working
    * Entity
    * Player
    * Enemy
    * Terrain
    * Projectile
    * Item
* This involves writing the basic rules for how objects react to collisions
    * For example, a player can either be unaffected, die, or 


#### gjl13 (Grant): Game Controller and Physics
* Game Controller
    * Create files that hold unique users
    * Create files that hold the levels
        * Read the basicLevel files and pass a list of entities to the game engine
    * Get basicLevel selected from view
    * Manage launching of the game
        * Creating the view, game engine, and entities
    * Write basic 'land' physics class that governs the way objects move
    * Manage user stats, including lives and points earned
        * When a player loses a life
            * Remove a life from the User
            * Tell the view to show "basicLevel over, x lives left (Game Over if applicable)"
                * Restart the basicLevel (create a new basicLevel controller) if there are remaining lives
                * Return to Level Selector screen if no lives left
* Physics Engine
    * 

#### cas169 (Cayla): Game Engine
* Take in a list of entities from LevelController and set which entities should be active on the screen
* Create a Timeline that constatly checks for UserInput, Collisions, updates the EntityList, and updates the position of the Camera.
* Tell the LevelController which entities to display
* Check for UserInput to move the main character
* Adjust the entities that are displayed based upon the movement of the main character
    * Mark entities as non-visible when they move off the screen
    * Mark entities as visible when they move on the screen
* Check for collisions between entities
    * Pass collisions down to model through collision manager
    * Remove entites marked as gone
    * Add new entities that are created
* Send a collection of on screen entities back to game controller after a game cycle / keep track of what entities are on screen, removing "dead" or destroyed objects


#### to57 (Thomas): View and Collision Files
* Create properties files with the hierarchy of managing collisions
* When a player loses a life, show 'death screen' for some time and then display the new basicLevel screen (MAYBE THIS GOES WITH GRANT?)
* If a player runs out of lives, show game over screen
* Start up screen with a button to go into selecting levels
* Display a selected basicLevel
* Create all standard menu screens, as described in DESIGN_PLAN
* Create error and message popups to tell the user of atypical execution.

### Secondary Responsibilies

#### dmm107 (Dana)
Assist other teammates that need help, particularly working on the physics engine, and using reflection in the reading in of basicLevel files to create entities and handling collisions.

#### gjl13 (Grant)
Assist other teammates that need help. Work with Cayla on the CameraManager.

#### cas169 (Cayla)
Assist Thomas in the View. Use reflection with button actions. Help any other teammate that is behind in their responsibilities. 

#### to57 (Thomas)
Assist other teammates that need help. Work with Cayla on the GameEngine.

