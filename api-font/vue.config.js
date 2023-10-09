const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {

    proxy: {
      '/api': {
        target: 'http://localhost',
        // target: 'http://ledger-code.buzz:9999',
        changeOrigin: true, // 允许跨域
        pathRewrite: {
          // '^/api': '/'
        }
      }
    }
  },

  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          // 在这里添加自定义的Less配置
        },
      },
    },
  },

  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: []
    }
  }
})
