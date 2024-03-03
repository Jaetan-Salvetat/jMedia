# Sample Informations
### Works types
- [x] Mangas
- [x] Animes
- [x] Books
- [x] Movies
- [x] Series
- [ ] Video games

### Bugfix
- [x] improve the quality of the image in the search view
- [x] Need to add a placeholder when we can't display the image
- [x] Film and series synopsis are empty in some cases
- [x] Release Date isn't in all medias, crash on null value in, movies/series

### Tech
- [x] Create an build config object
- [x] Create an interface for ream repositories

### Global Features
- [x] Implement an analytics
- [ ] Determinate what we've need to track with analytics
- [ ] Need to save images locally
 
# Screens
### Onboarding
- [ ] Show the onboarding at the first app start 
- [ ] Make onboarding button in the settings view
- List of content:
  - [ ] Application purpose explanation
  - [ ] View Customization
  - [ ] Theming Customization
  - [ ] Data storage explanation
  - [ ] Thank for reading: "contact for feature idea, bug, and stuff in settings view"
 
### Library
- [x] Show all works as carousel (max 10)
- [x] Hide empty works type list
- [x] Display a button to show all
- [x] Open to the bottom sheet that display all (and display all)
- [x] Show empty state
- [ ] Can open **work detail** screen
- Can search a work by
  - [x] name
  - [ ] author
  - [ ] genre
- [ ] Can filter works
- [ ] Remove one or more works from library

### Search
- [x] Can search a work
- [ ] Can open the **work detail** screen
- [x] Can add a work to library
- [x] Show the empty/default/error states
- [x] display work types list tab
- [x] save previous search on work type change
- [x] display result of selected types
- can sort by (ascending and descending):
  - [x] title
  - [x] rating
  - [x] default (only if just one of work type is selected)

### Work detail
- [ ] Can show all work details
- [ ] Can add or remove a work from library

### Settings
- [x] Design the main view
- [x] Delete data
- View Customization
  - [ ] Add multiples themes
  - [ ] Can manually change between light, dark and system theme
  - [ ] Add a pure dark (only for dark themes)
  - [ ] ~~Can change the default list type~~
  - [ ] ~~Can Edit the grid list size~~
- [ ] User feedback (Google form ?)
- Display OnBoarding
  - [ ] Select works type
  - [ ] Select theme
- [x] Display Version and build