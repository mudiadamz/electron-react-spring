# Electron-React-Spring

> An opionated desktop application with web front-end and Java backend.

In some cases, you may like to use Java backend for an Electron desktop app. The reasons could be you have some legacy Java codes that you want to reuse, or you want to have the same codes run on Cloud as well as on desktop.

This project has two sub projects. Although they are just folders in this project, but they could be in their own Git repository and merged to this project using [Git subtree](https://help.github.com/articles/about-git-subtree-merges/).

1. `reacr`: a React application as the front-end, based on the HelloWorld project created using create-react-app.
2. `spring`: a Spring Boot application as the backend, based on a Maven project created by [Spring Initializer](https://start.spring.io/) with Web dependency.

Both Linux and Mac OS are supported (i am not testing it yet, but there's shouldn't be any big problem.

> NOTE: This project uses your system Java to run the spring web app. If you prefer to bundle JRE into the app, configure the `extraFiles` of Electron Builder to copy it when making the installer.

## Build Setup

Build the final installer, which can be found in folder `dist`. It is an `exe` file for Windows and `dmg` file for Mac.

``` bash
# install root dependencies
npm install

# install dependencies for react project
cd react-gui
npm install

# build spring and installer for production

I included `mysql` on spring module, so make sure that you check the `spring/src/main/resources/application.properties` and change the configuration acordingly

then : 

npm run build

to build the entire project

# for testing
npm run start
```

## How it works

The main idea is to use Electron as a browser, and the front-end and backend of the app work as a web app. It might not be a common design, but is helpful in some cases.

The backend is a typical Spring Boot app, serving API to the front-end. The front-end is a typical Vue app, consuming API from the backend. 

### Build process

When building the final desktop app installer:

1. Front-end is built first. The final artifacts, including `index.html` and JavaScript files, are copied into `spring/src/main/resources/public` folder. 
2. Backend is built second. It creates a web app with the front-end artifacts created above and an executable jar.
3. Electron installer is built last. It includes the web app created above in the bundle and creates an executable installer.

However, both `react` sub project and `spring` sub project are free of Electron and can be built independently without building the Electron part. This allows them to be deployed online, instead of packaged into Electron app.

### Launch process

When launching the Electron app:

1. Electron app detects an available port and starts the backend server with Node `child_process` at the specified port. The PID of the server process is kept to kill the process before quiting the app.
2. Electron app then displays a splash page, at the same time pings the URL of the backend server.
3. Once the ping returns OK (the web app is up), Electron app switches the page to the home page of the web app.

### Security

Although the Java backend is running locally, it may be more secure to load the page with Node integration disabled. This prevents third-party JavaScript libraries used by your web app from accessing Node directly, and mitigates the risk if your app navigates to external website.

The access to Node can be selectively re-introduced back to the web app via `preload.js`, which defines a set of API on a global `window.interop` object. Upon launch, this object is assigned to `Vue.prototype.$interop`, making it available to all Vue components in your app. 

### Logging

The log messages from Electron, Vue and Spring apps are consolidated into the [electron logger](https://www.npmjs.com/package/electron-log) in Electron app. By default it writes logs to the following locations:

* on Linux: ~/.config/<app name>/log.log
* on macOS: ~/Library/Logs/<app name>/log.log
* on Windows: %USERPROFILE%\AppData\Roaming\<app name>\log.log

In the Vue app, the electron logger is wrapped by the `log` property of `window.interop` object. During launch, this `log` is set as `Vue.prototype.$log` and `Vue.$log` in `main.js`. Calling `vm.$log.info(...)` or `Vue.$log.info(...)` will send the log messages (after attaching a prefix to identify it is from UI) to electron logger. Other logging level works in the same way.

In the Spring app, `logback-spring.xml` configuration sends the log to console, which is the standard output received by the Electron app. The logback message pattern put the log level (`INFO`, `DEBUG`, etc.) at the begining of the message so that Electron app checks and calls the corresponding function (`info`, `debug`, etc.) on the electron logger.

## License

[MIT](LICENSE)

This starter is copied from [wuruoyun/electron-vue-spring](https://github.com/wuruoyun/electron-vue-spring), but because i don't like `vue` so i changed it to react
