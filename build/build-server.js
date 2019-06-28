const shell = require('shelljs');

shell.echo('#     Building react       #');
shell.cd('react-gui');
const PUBLIC = '../spring/src/main/resources/public/';
shell.rm('-rf', PUBLIC);
if (shell.exec('yarn run build').code !== 0) {
  shell.echo('Error: react build failed');
  shell.exit(1)
}
shell.cp('-R', 'build/', PUBLIC);
shell.cd('..');

shell.echo('#     Building spring    #');
shell.cd('spring');
const mvnw = process.platform === 'win32' ? 'mvnw' : './mvnw';
if (shell.exec(mvnw + ' clean package').code !== 0) {
  shell.echo('Error: spring build failed');
  shell.exit(1)
}
