const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    // 不会影响ws服务
    proxy: {
      '/api': {
        ws: false,
        target: 'http://localhost:12000',
        changeOrigin: true, // 允许跨域
        pathRewrite: {
          // '^/api': '/'
        }
      }
    }
  },
  //生产环境去除控制台console.log的打印
  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions = {
        compress: {
          drop_console: true
        }
      };
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
