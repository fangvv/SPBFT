%read data into matirx
%http function 
fid = fopen('gstime.txt','rt'); %首先打开文件把数据读取出来
C = textscan(fid, '%f %f', 'Delimiter',' ');
fclose(fid); %之后把数据扫描进C这个变量中,我们是按照浮点类型的形式来处理我们的数据的
data1 = deal(C{1});% 每一列数据表示的一次随机实验中取到的随机变量,所以我们分别处理
data2 = deal(C{2});
%data3 = deal(C{3});
x = 4:3:22;%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%实验次数

h1 = plot(x ,data1);% 在matlab中画图我们使用cdfplot,这个命令
h1.Marker = '*';
h1.Color = [0 0 1];
hold on;

h2 =plot(x, data2);
h2.Marker = 'o';
h2.Color = [1 0 0];
hold on;

%h3 =plot(x, data3);
%h3.Marker = '>';
%h.Color = [0 1 0];
hold on;
axis([4 22 30 180]);%%%%%%%%坐标
set(gca,'XTickMode','manual','XTick',[4 7 10 13 16 19 22]); 
xlabel('\fontname{SimSun}节点数量','FontSize',14); 
ylabel('\fontname{SimSun}完成共识时间/\fontname{Times New Roman}ms','FontSize',14);
%title('\fontname{SimSun}PBFT-SHPBFT共识时间对比','FontSize',14)
%set(legend,'Box','off','FontSize',14);
%box off;
%xl=xlim;
%yl=ylim;
%line([xl(1),xl(2)],[yl(2),yl(2)],'color',[0 0 0]);   %画上边框，线条的颜色设置为黑色

%line([xl(2),xl(2)],[yl(1),yl(2)],'color',[0 0 0]);    %画右边框，线条的颜色设置为黑色
%zlabel(['\fontname{宋体}理论值[m]\fontname{Euclid}this is english!\fontname{宋体}又是中文！ '])
legend( {'\fontname{Times New Roman}PBFT','\fontname{Times New Roman}SPBFT'},'FontSize',12);
grid on
set(gca,'GridLineStyle',':','GridColor','k', 'GridAlpha',1)
