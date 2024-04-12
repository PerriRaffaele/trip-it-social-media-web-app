const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: '/',
  devServer: {
    proxy: {
      // '/activities': {
      //   target: 'http://localhost:8080',
      // },
      // '/journey': {
      //   target: 'http://localhost:8080',
      // },
      '/api' : {
        target: 'https://timeapi.io',
        changeOrigin: true,
      },
      '/login': {
        target: 'http://localhost:8080',
      },
      '/users': {
        target: 'http://localhost:8080',
      },

    }
  },
})
