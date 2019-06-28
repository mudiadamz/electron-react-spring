const shell = require('shelljs');

shell.echo('#    Building electron   #');


if (!shell.test('-e', 'spring/target')) {
  shell.echo('Error: server is not built yet.');
  shell.exit(1)
}

shell.rm('-rf', 'dist');
if (shell.exec('build').code !== 0) {
  shell.echo('Error: electron build failed');
  shell.exit(1)
}
