df.custom.all <- function(n, k1, k2) {
  data.frame(xemp=f.dist(n=n, k1=k1, k2=k2),
             y='Рівномірний - генератор Ковею')
}

df.custom.beta <- function(n, k1, k2) {
  rbind(df.custom.all(n, k1, k2),
        data.frame(xemp=f.dist(n=n, k1=k1, k2=k2, unif.custom=F),
                   y='Рівномірний - системний генератор'))
}

df.all <- function(n, k1, k2) {
  rbind(df.custom.beta(n, k1, k2),
        data.frame(xemp=f.dist(n=n, k1=k1, k2=k2, unif.custom=F,
                               beta.custom=F),
                   y='Бета - системний генератор'),
        data.frame(xemp=rf(n=n, df1=k1, df2=k2),
                   y='Фішера - системний генератор'))
}

f.plot <- function(fun.df=df.all, n=10000, k1, k2, xmax=5, precision=.1) {
  f.df <- fun.df(n, k1, k2)
  ggplot(f.df, aes(x=xemp, ..density.., colour = y)) +
    geom_area(alpha = .1, binwidth = precision, position="identity", stat="bin") +
    xlim(0, xmax)
}