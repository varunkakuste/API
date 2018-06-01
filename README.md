# API

## Requirements

- Gradle 2.14
- node v6.9.5
- npm version 3.10.10
- angular/cli: 1.3.2 (make sure using ng --version)
- java version 1.8.0_91
- Lombok plugin
- IntelliJ Idea

## How to start all services

### Start the apiserver

This service manages the API server, to expose API to end user

```bash
cd to the cloned directory
./gradlew clean apiserver:assemble --refresh-dependencies
./gradlew clean apiserver:build
./gradlew clean apiserver:bootrun
```
Now, the server is running at http://localhost:8080/

### Start the apiportal

This service is basically the user interface which exposes the different API available.

```bash
cd to the cloned directory 
cd apiportal
npm install
ng serve
```

Now, hit http://localhost:4200 in the browser and follow the instructions

#### Note
- When we click the `Get Marker Details` button, it takes 15-25 seconds to read and display details.

### Understanding and Assumptions

- `neanderthal_variants.txt` has the unique neanderthal DNA samples
- A marker may have or may not have neanderthal variant. In addition to the neanderthal variant, it will have it's own variants too
- I didn't have multiple profile's for testing so, I have used `demo` profile but the HTML form accepts ProfileId and Access Token, as long as input is right it will fetch the data and display else it will fetch data for `demo_profile_id` using `demo_oauth_token`
  
### Coding 

- Parsed the `neanderthal_variants.txt` and got all the accession_id's
- Get all the marker's for each accession_id
- Now, we will have marker and it's variants, iterate over each marker and check if it's variants are present in `neanderthal_variants.txt`. 
If so, get the `dosage` and store it
- In the end, add all the `dosage` of the marker's variant matched with the neanderthal's variant and display it on the browser

## Open Project in IntelliJ Idea
- Open IntelliJ Idea
- Select open and navigate to the cloned project directory and open it
- Follow the instructions, make sure to select correct gradle and java while opening the project

## Import lombok plugin

We are using Lombok project which can automatically generates getters, setters, equals, hashCode and more.For more information, go to `https://projectlombok.org/index.html`.

To avoid having error in your Intellij project, you should import the Lombok plugin:

<kbd>Preferences</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "lombok"</kbd> > <kbd>Install Plugin</kbd>
