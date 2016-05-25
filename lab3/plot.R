get.dataframe <- function(n, k1, k2) {
  rbind(data.frame(xemp=f.dist(n=n, k1=k1, k2=k2),
                   y='Рівномірний - генератор Ковею'),
        data.frame(xemp=f.dist(n=n, k1=k1, k2=k2, unif.custom=F),
                   y='Рівномірний - системний генератор'),
        data.frame(xemp=f.dist(n=n, k1=k1, k2=k2, unif.custom=F,
                               beta.custom=F),
                   y='Бета - системний генератор'),
        data.frame(xemp=rf(n=n, df1=k1, df2=k2),
                   y='Фішера - системний генератор'))
}

f.plot.compare <- function(n=10000, k1, k2, xmax=5, precision=.1) {
  f.df <- get.dataframe(n, k1, k2)
  ggplot(f.df, aes(x=xemp, ..density.., colour = y)) +
    geom_freqpoly(alpha = .5, binwidth = precision, position="identity") +
    xlim(0, xmax)
}