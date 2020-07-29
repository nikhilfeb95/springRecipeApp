Why did we make Commands and Converters and not use the domain objecs directly?
-> What we expose to the web is sometimes vastly different than what we require from the domain objects. Why?
Domain objects go through changes throughout the project but what we require for the web part hardly does. Thus, its better and
practical to seperate them out. What converters are gonna do is convert the objects from MVC to domain and do taks on them and
similarly convert them when we send it from the domain to the web.