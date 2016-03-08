// 引入 gulp
var gulp = require('gulp');
var sass = require('gulp-sass');

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