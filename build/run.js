const shell = require('shelljs');

shell.cd('spring');
shell.echo('#Start springboot#');
shell.exec('mvnw spring-boot:run', { async: true });

shell.cd('../react-gui');
shell.echo('#Start react#');
shell.exec('yarn start', { async: true });

shell.cd('..');
shell.echo('#Start Electron#');
shell.exec('electron ./electron', { async: true });
