# API

## Requirements

- node v6.9.5
- npm version 3.10.10
- angular/cli: 1.3.2 (make sure using ng --version)
- java version 1.8.0_91
- Lombok plugin
- IntelliJ Idea

### Import lombok plugin

We are using Lombok project which can automatically generates getters, setters, equals, hashCode and more.For more information, go to `https://projectlombok.org/index.html`.

To avoid having error in your Intellij project, you should import the Lombok plugin:

<kbd>Preferences</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "lombok"</kbd> > <kbd>Install Plugin</kbd>

## How to start all services

### Start the apiserver

This service manages the API server, to expose API to end user

- cd to the cloned directory and then 
```bash
cd to the cloned directory
./gradlew clean apiserver:assemble --refresh-dependencies
./gradlew clean apiserver:build
./gradlew clean apiserver:bootrun
```

### Start the apiportal

This service is basically the user interface which exposes the different API available.

```bash
cd to the cloned directory 
cd apiportal
npm install
ng serve
```

Now, hit http://localhost:8080/ in the browser and follow the instructions

### Understanding and Assumptions

- `neanderthal_variants.txt` has the unique neanderthal DNA samples
- A marker may have or may not have neanderthal variant. In addition to the neanderthal variant, it will have it's own variants too
  
### Coding 

- Parsed the `neanderthal_variants.txt` and got all the accession_id's
- Get all the marker's for each accession_id
- Now, we will have marker and it's variants, iterate over each marker and check if it's variants are present in `neanderthal_variants.txt`. 
If so, get the `dosage` and store it
- In the end, add all the `dosage` of the marker's variant matched with the neanderthal's variant and display it on the browser
