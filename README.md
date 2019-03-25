# Demo Android App for Graph-based Context-aware Holistic Recommendations

This is the code for the Android App used to demo our API built for the Graph-based Context-aware Holistic Recommendations.

### Prerequisites

Add the volley library dependency in the dependencies section of your build.gradle file.

```
implementation 'com.android.volley:volley:1.1.1'
```

## How it Works

The App will asks users to input some data: name, categories of places they like to frequent and the user context or the context in which they would like to receive suggestions of places to go to.

Then the App will use the data collected and make a POST request to our API service at :
```
https://graph-recommender.herokuapp.com/recommendation/post
```

The API endpoint will return the suggestions that the App then shows to the user.

## Built With

* Android Studio
* Gradle

## Authors

* **Marco Mirizio**
* **Federico Impellizzeri**


## Acknowledgments

* Prof. **Cataldo Musto**
