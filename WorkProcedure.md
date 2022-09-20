# Work procedure for connecting frontend to backend in Android (basics)

1. Create your XML components, with an `id` for each View.
2. For each component that needs to appear, create a variable to store that View. For example, a `<Button>` was declared in XML file. 
3. Now go to your Java file and create a variable of the same type, for example `Button button`.
4. If your View is going to display something that is stored, you need to declare 2 more variables in your Java file. For example, a list displaying city names. You will of course have `<ListView>` in XML file and `ListView cityList` in Java file. You will also have `ArrayList<String> dataList` to store data, and `ArrayAdapter<String> cityAdapter` to transfer data to the view.
5. Next, you would have to assign values (views) to the View variables you created in Java file. Use `findViewById` and `id` declared in XML components to do so. Do it for every View.
6. Now you would want to pass data from your array into the adapter. You can do so by creating the adapter object and assign it to the adapter variable.
7. Next, use `setAdapter` method to pass the adapter to the view variable.

The code for the city list in the Java file after those steps would look like this
```Java
ListView cityList;
ArrayList<String> dataList = new ArrayList<>();
ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
cityList.setAdapter(cityAdapter);
```
Note that the `R.layout.content` is referring to an XML file (in this case named `content.xml`), which decribes how a cell in the ListView should look like.