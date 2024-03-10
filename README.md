# Drawing application for Android OS

A drawing application was developed to explore the creation of custom views. There are two non-standard views used here:
* custom canvas on which drawing is performed;
* custom tools panel that supports adding an unlimited number of different items. Includes a *"parent" panel* for selecting a tool and *"child" panels*, each of which contains individual options for configuring canvas parameters.

<p float="middle">
  <kbd> <img src="https://github.com/alexadler9/canvas/assets/56451293/e1e5997a-56fc-4234-a553-52c3ce0bc1dd" height="320" /> </kbd>
  <kbd> <img src="https://github.com/alexadler9/canvas/assets/56451293/1bcf4a8e-8fd8-4883-ab2b-e384dbb5c885" height="200" /> </kbd>
</p>

## Tech stack and open-source libraries
* **_Kotlin_** based, **_Coroutines_** for asynchronous (including channels and flows).
* Architecture:
  - **_MVI_** architecture (Model-View-Intent);
* **_Dagger_** & **_Hilt_** - for dependency injection.
* **_Jetpack_** libraries:
  - _ViewModel_ - UI related data holder, lifecycle aware;
  - _ViewBinding_ - simplified interaction with views;
  - _RecyclerView_ - view for providing a limited window into a data set.
* **_SharedPreferences_** - to store user preferences.
* **_adapterdelegates4_** - to support the "Delegate Adapter" pattern in RecyclerView, which allows to separate the View’s logic into different adapters.
* Testing:
  - **_JUnit5_** & **_Mockito_** for unit tests;
  - **_Espresso_** for UI tests.
