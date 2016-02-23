/**
 * Created by supersoup on 16/1/17.
 */
var gulp = require('gulp');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');

//创建style任务
gulp.task('style', function() {
    //源路径
     gulp.src('./resources/*/*.scss')
         .pipe(sourcemaps.init())
         //进行sass编译
        .pipe(sass())
         //生成map文件
         .pipe(sourcemaps.write('../maps'))
         //生成css文件在目标路径
        .pipe(gulp.dest('./resources'));
});

// 监听
gulp.task('watch', function() {
    gulp.watch('./resources/**/*.scss', ['style']);
});

//把监听设为默认任务
gulp.task('default', ['watch']);
