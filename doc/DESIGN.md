# DESIGN.md

### Names of all people who worked on the project

Thomas Owens (to57), Grant LoPresti (gjl13), Cayla Schuval (cas169), Dana Mulligan (dmm107)

### Development Roles

**Cayla:** Level Controller. 
- Levels, Entity Management, Camera Managers, Key-Input Management, Collision Detection

**Dana:** Model. 
- Entities, Abilities, Collision Behavior

**Thomas:** Front End. 
- JavaFX View, Custom Layout & Styling, Screen Functions, Exception Handling

**Grant:** Game Controller. 
- Communication, Game, Levels, Users, & Physics

### Design Goals
- What are the project's design goals.
    - Allow users to, without interracting with the code, make drastic changes to an existing game, or even create their own game, though simple manipulation of gamedata files.
    - Make the project as flexible and extensible as possible
        - We wanted it to be a very quick process to add an additional game!
    - Hide implementation of individual classes
    - Sucessfully apply Open/Close Design Principles to our Design
- What kinds of new features did you want to make easy to add?
    - Entities associated with different images
    - Different collision reactions between 2 entities
    - Scrolling types
    - Entity movement
    - Colors/ words on the display
    - Keys can be associated with different methods
    - Can easily change the result of holding down a key

### High Level Design
- Describe the high-level design of your project
    - At a high level, the project is split into four core packages: the view, controller, engine, and model. 
    - The *view* contains information relevant to the layout, styling, and functionality of the screens displayed when you play the game. 
    - The *controller* controls switching between these screens, and also facilitates interaction between the view and the backend. This includes game-state updates from the engine, along with controller-level interactions like saving/loading users and levels to/from files. 
    - The *engine* holds the "game loop" that drives the game's incremental updates, managing which entities are displayed on screen, user input, entity deaths and creation, and collisions.
    - Finally, the *model* holds the game state, including data about entities being displayed on screen and their movement and abilities. 
    - These four core packages interact in a linear way: the view only interacts with the controller; the controller only interacts with the view and engine; the engine only interacts with the controller and model; and the model only interacts with the engine. This "chain of command" maintains separation of model and view by abstracting functionality to two levels of controllers, which handle higher-level game logic and execution, while the model holds only low-level information and simple functionality.

### Assumptions or Decisions (made to simplify your project's design)
- That the main player of Mario should be Mario.
- That if a person wants to create a new game, they understand how the data files should be formatted.
- Error messages will always being in English, as they are often formed with information relevant to the error (could cause grammatical errors!)


### Adding Additional Features

#### Adding a New Scrolling Game:

Main class: `Main.java`. Run this! If you get an error pop up, please exit it responsibly.

Data files needed: For each game type, you need a directory within `data\gamedata` of the game name. Within that game directory, you need to have four directories: `entities`, `levels`, `physics`, and `userinput`. On the same level, you also need a `gameBackground.png`, `gameIcon.png`, and `gameStyle.css`. Here is a visual tree of everything you need:

* data 
    * gamedata
        * gameType (NAME UP TO YOU)
            * entities
                * behavior
                    * All different entity types
                    * For example: Coin.properties, Player.properties, etc.
                * collisions
                    * All different attack types, and their relations
                    * Each attack type (from the behavior files) needs a resource file with *each* attack type and the methods that should be called as a result. You can pick any methods that are in the MethodHosts.properties file from resources!
                * images
                    * All image files needed for entities
                * entities.properties
                    * Holds relations between the level file and the entity files
            * levels
                * backgrounds
                    * The levelBackground.png file to exist behind each level's entities
                * resources
                    * Graph for the assignment of which levels unlock other levels
                    * Map for the placement of each Level Dot on the LevelSelectorScreen
                    * Order for the ordering of the level files to the order they are unlocked
                * levels
                    * Holds all level files required to have appropriate/correct formatting, that define relationships between the level
            * physics
                * physicsconstants
                    * multi level design. Top level contains all constants required for the physics classes to work for mario. GameSpecific physics constants override these for other games
            * userinput
                * userinput
                    * relates each game's controlls to the appropriate methods in physics that should be called
            * view
                * resources
                    * any overwritten or extra view resource files
                * images
                    * any overwritten or extra view images
                * any overwritten or extra .FXLR layout files
            * gameStyle.css
                * game-specific CSS styling, overrides main stylesheet.css
    * userdata
        * images
            * here is where you add another image if you create another user
        * users.properties
            * links each user property to methods needed for parsing 
        * User files
            * holds information pertaining to each user. For example, their lives, points, and levels unlocked per each game.
    * view
        * resources
            * resource files for the button actions and text/image paths used by each screen in the view
        * images
            * any images used by the view
        * FXLR layout files for each screen used in the game, which hold layout information
        * gamedata/\*/view folders can override any/all of the files in this view to enable custom layouts and styling per game
    * stylesheet.css
        * CSS styling for the application as a whole

#### Implementing a new direction controller:
- Determine which package the direction controller should belong in (horizontal, vertical, centered)
- extend the proper super class and write the method updatePositions to contain information regarding how the camera should move based upon the main character's location

#### Adding in more keys to correspond to events:
- add the key code into the userinput properties file along with the associated method that is located within the Entity class that contains the corresponding method to be called
- add the key code + "ONREPEAT" along with "repeat" or "once" to correspond if the method should be repeated if the key is held down or if the method should only execute once

#### Changing Physics Constants
- Based off of the defaultphysics.properties file, add overriding keys and their new values to the game specific gamephysics.properties files

#### Making a Duplicate Entity, but with a different image
- Simply add another Key to the .level file in question with the name of the "new" entity type. 
- In the entities.properties file, add another line with the "new entity type" as the key, the pre-existing entitiy behavior it should emulate, a comma, and the specific image file that it should use.