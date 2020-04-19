# PRESENTATION_3.md

*Start by showing off the features of your running program:*

* Run through of project like last time!
    * user interface - thomas
        * user select
        * new user screen
        * show levels appearing based on progress
    * level select
        * grant
    * running through a level
        * cayla then dana
* Talk about the progress in this sprint - everyone, in same order as before
    * significant code changes (in words)
    * show data files
        * focus on file content not on the code itself
* Talk about testing/the lack of it - dana
* what impeded progress - cayla
    * remote learning
* what helped progress - cayla
    * meetings on tuesdays and thursdays
* specific event that occured - dana
    * we noticed that we weren't checking for collisions the way that we thought we were, so we caught a lot of bad behavior that was happening as a result (that we thought was correct). This led to an overhaul in changes of collision behavior, and adding of a second layer out routing through
* plans for next sprint - everyone

## Sprint 2 Demo
* Run the program from the master branch through a planned series of steps/interactions (it will look much better if this demo shows everyone's parts working together as a single program rather than multiple separate demos)
everyone

* show any data files that have been created and describe how they are used within your program (focus on the file content rather than the code that uses it)

everyone

* show a variety of JUnit or TestFX tests (including both happy and sad paths) and discuss how writing tests helped or affected the way the code was written

* **Dana** 

* if asked, everyone should be able to describe how their work relates to specific demoed features as they are shown

#### Then present what you (or your team overall) learned during this Sprint and the implementation plan for the next Sprint:
* describe how much of the planned features were completed this Sprint and what helped or impeded progress
    * **cayla**
        * remote learning

* describe a specific significant event that occurred this Sprint and what was learned from it
    * **Dana**
        * we noticed that we weren't checking for collisions the way that we thought we were, so we caught a lot of bad behavior that was happening as a result (that we thought was correct). This led to an overhaul in changes of collision behavior, and adding of a second layer out routing through


* describe what worked, what did not, and something specific that is planned for improvement next Sprint

## Complete Plan

### Basic Stuff
* General Fixes
    * Lives Visually Update
    * Points Visually Update
    * Death ends level correctly
    * Unlocking next level at end of level
    * Able to walk on Bricks correctly
    * Correcting Side Collisions
    * Correcting Physics Classes/Different for different games
* Testing, a lot (JUnit & TestFX)
* Error Throwing for any issues
* Possibly change collision events to pass in an interface of the other entity
* Bugfixes for existing functionality
* Mario breaking bricks


### Complete Stuff
* Implement another game type, like Flappy Bird
    * Different texture package
* Level Builder
    * Input Validation Works
    * Entity Selector Designed & Created
* Enemy movement (& Deaths etc.)
* ~~Pipes to New Levels?~~
* 
* being able to hit question-mark boxes
    * dropping of powerups


## Individual Responsibilities for the next sprint:

##### Grant:
- General Fixes
    - Lives Visually Update
    - Points Visually Update
    - Death ends level correctly
    - Unlocking next level at end of level
- Different texture package
    - Resource Files for Image Selection per entity
- Implement another game type, like Flappy Bird
    - Images for that
    - Levels for that
    - Physics for that
- Physics Types & Proper selection per level
    - One per Game Type (Mario, Flappy, etc.)
- Testing, a lot (JUnit & TestFX)
- Error Throwing for any issues 


##### Thomas:
- [ ] Write TestFX UI tests for each screen and for each combination of screens
- [ ] Add a help screen
- [ ] Help Grant with the level builder?
- [ ] Support the rest of the team
    
##### Cayla:
* Implement testing
* Error throwing
*  
    
##### Dana:
- [ ] More testing
    - [ ] figure out why correct tests are not even built to begin with
- [ ] Error throwing
    - [ ] currently, everything is being handled as run time errors. change to our specific error implementation, and decide if any should break the program
- [ ] Change CollisionEvent to hold the location and an interface of the entity to try and make it where the effects of a collectible item happen on the entity that hit it, not on the collectible entity itself
- [ ] fine tuning collision behavior
- [ ] * Able to walk on Bricks correctly
- [ ] * Correcting Side Collisions