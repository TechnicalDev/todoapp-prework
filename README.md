# Pre-work - *SimpleToDo*

SimpleToDo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Shyam Sundar Ashok

Time spent: 15 hours spent in total

## User Stories

The following **required** functionality is completed:

* [ ] User can **successfully add and remove items** from the todo list
* [ ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [ ] User can **persist todo items** and retrieve them properly on app restart


The following **optional** features are implemented:

* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Android app development is interesting as the app is built native to the OS. I have mostly worked on web based apps and making them responsive to fit in a mobile/tablet layout.
 I am currently looking into developing hybrid apps with ionic and reactive native which is packaged natively into android.
 
 **Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."
 
 **Answer:** An adapter is an object that provides views for a list view. Whenever list view needs to draw a view at a particular list position, it gets it from the adapter. The adapter makes the view for that position and returns it. Since the adapter creates the views, it is necessary for it to also store the underlying data. So the adapters are all about this. They pack data and the logic for creating views out of the data. The data can be a cursor or a list of objects. To support these different types, we have CursorAdapter and ArrayAdapter in the SDK.
ArrayAdapter stores the data in a list. ArrayAdapter class has getView() method that is responsible for creating the views. So behind the scenes, a listview calls this method to get a view for a particular position.

A convert view is a view that is currently not in the screen and hence can be recycled. This results in big performance boost as we reuse an existing view instead of inflating a new view from the xml layout file which is a relatively costly operation.
To get the fastest list view possible, use convert view if it is not null.

## License

    Copyright [2017] [Shyam Sundar Ashok]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.