const { configure } = require('quasar/wrappers');

module.exports = configure(() => ({
  boot: ['axios', 'component', 'app'],
  css: ['app.scss'],
  extras: ['mdi-v7', 'roboto-font-latin-ext', 'material-icons'],
  build: {
    target: {
      browser: ['es2019', 'edge88', 'firefox78', 'chrome87', 'safari13.1'],
      node: 'node20',
    },
    vueRouterMode: 'history',
    publicPath: '/',
    vitePlugins: [
      [
        'vite-plugin-checker',
        {
          vueTsc: {
            tsconfigPath: 'tsconfig.vue-tsc.json',
          },
          eslint: {
            lintCommand: 'eslint "./**/*.{js,ts,mjs,cjs,vue}"',
          },
        },
        { server: false },
      ],
    ],
  },
  framework: {
    lang: 'pl',
    plugins: ['Dialog', 'Loading', 'Notify'],
  },
}));
