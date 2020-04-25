# READ_ME.md

final
====

This project implements a player for multiple related games.

Names: Thomas Owens (to57), Grant LoPresti (gjl13), Cayla Schuval (cas169), Dana Mulligan (dmm107)


### Timeline

Start Date: March 29th, 2020

Finish Date: April 24th, 2020

Hours Spent: Many. Hours. (At least 300 cumulative)

### Primary Roles

Cayla: *Level Controller.* Levels, Entity Management, Camera Managers

Dana: *Model.* Entities, Abilities, Collision Behavior

Thomas: *Front End.* JavaFX View, Custom Layout & Styling, Screen Functions, Exception Handling

Grant: *Game Controller.* Game, Levels, Users, & Physics

### Resources Used
Class lectures, class articles, StackOverflow.

### Running the Program

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

* resources
    * abilitytypes
        * contains information regarding the abilities that an entity can have
    * collisionmanagement
        * contains information regarding where collision occurs
    * directioncontrollers
        * contains information regarding all possible directioncontrollers
    * entitymethods
        * contains which methods should be called from different interactions
    


#### Features implemented:
Four different game types; different css, images, and rules between them. Users save information, including lives, points, and other current information regarding game progress. 


### Notes/Assumptions

#### Assumptions or Simplifications: 
- That the main player of Mario should be Mario. 
- That if a person wants to create a new game, they understand how the data files should be formatted
- Error messages will always being in English, as they are often formed with information relevant to the error (could cause grammatical errors!)

#### Interesting data files:
- gamedata/mario/levels/TopOfTheWorld.level
    - mario has to avoid entities and has the option of getting to the top of a ground brick to avoid the enemies
- gamedata/mario/levels/VerticalV2.level
    - implementation of a vertical level in mario

#### Known Bugs:
* Mario Game:
    * If mario collects a mushroom that changes his size, the size of the hit box doesn't change (just the image). This means that a big mario can visually intersect things without anything happening, and a small mario can not be touching something but things happen.
* Flappy Game:
    * Even though coins are implemented the same way as in the Mario game, the points collection doesn't visually update on screen. Unit tests for collection pass in the backend. 

#### Extra credit:
Game content loads dynamically; beating a level will allow a user to unlock a set of following levels. Players can choose from four different games to play, and their scores will carry over to allow for maximum score.


### Impressions
Our group really enjoyed working on this project. We feel that we have all grown as coders and that we have learned a lot about game development. Further, we feel that we have grown significantly as a team in regards to our communicate skills considering that we created this entire project remotely. 

