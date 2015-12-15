// 引入 gulp
var gulp = require('gulp');

// 引入组件
//var jshint = require('gulp-jshint');
var sass = require('gulp-sass');
//var concat = require('gulp-concat');
//var uglify = require('gulp-uglify');
//var rename = require('gulp-rename');


// 检查脚本
//gulp.task('lint', function() {
//    gulp.src('./js/*.js')
//        .pipe(jshint())
//        .pipe(jshint.reporter('default'));
//});

// 编译Sass
//gulp.task('sass', function() {
//    gulp.src('./resources/juedangpin/user/login.scss')
//        .pipe(sass())
//        .pipe(gulp.dest('./resources/juedangpin/user'));
//});
//
//// 监听文件变化
//gulp.task('watch', function() {
//    gulp.watch('./resources/juedangpin/user/login.scss', ['sass']);
//});


// 合并，压缩文件
//gulp.task('scripts', function() {
//    gulp.src('./js/*.js')
//        .pipe(concat('all.js'))
//        .pipe(gulp.dest('./dist'))
//        .pipe(rename('all.min.js'))
//        .pipe(uglify())
//        .pipe(gulp.dest('./dist'));
//});

// 默认任务
//gulp.task('default', function(){
//    gulp.run('watch');
//
//
//});
gulp.task('sass', function() {
    gulp.src('./resources/juedangpin/*/*.scss')
        .pipe(sass())
        .pipe(gulp.dest('./resources/juedangpin'));
});

gulp.task('sass2', function() {
    gulp.src('./resources/juedangpin/my-account/*/*.scss')
        .pipe(sass())
        .pipe(gulp.dest('./resources/juedangpin/my-account'));
});

gulp.task('watch', function() {
    gulp.watch('./resources/juedangpin/**/*.scss', ['sass','sass2']);
});