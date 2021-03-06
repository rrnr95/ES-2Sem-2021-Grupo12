### Grupo 12:
Nome|Número|User
----|------|----
António Rodrigo|87969|AntonioRodrigo92
Diogo Francisco|87596|DiogoFrnc
Erica Évora|87571|erikaevora
Renato Rosa|82836|rrnr95
Rui Gomes|88068|ruifogomes

- [Trello](https://trello.com/dozeteam/home)

<hr>
<strong>Utilização da Aplicação</strong>
<br>
<br>
O objetivo deste software é o levantamento de code smells de um dado projeto. Como tal, ao iniciar a aplicação, o utilizador deverá indicar a raiz do projeto que deseja analisar, através do botão <strong><i>Folder</i></strong>. O botão <strong><i>Calculate Metrics</i></strong> fará o levantamento das métricas do projeto, fornecendo um resumo geral do mesmo e criando um ficheiro XLSX na raiz deste. O botão <strong><i>Fetch XLSX</i></strong> apresenta todas as métricas do projeto, através da leitura do ficheiro XLSX previamente criado. 
De forma a definir as métricas usadas para os Code Smells, é possível gerir as regras através do botão <strong><i>Rules</i></strong>. A regra apenas ficará criada quando lhe for atribuida um nome não-existente e após pressionar o botão <strong><i>Add Rule</i></strong>.
<ul>
  <li>NOM_class: numero de metodos da classe</li>
  <li>LOC_class: linhas de código da classe</li>
  <li>WMC_class: complexidade ciclomatica da classe</li>
  <li>LOC_method: numero de linhas de codigo do metodo</li>
  <li>CYCLO_method: complexidade ciclomatica do metodo</li>
</ul>
o botão <strong><i>Classification Quality</i></strong> irá apresentar uma matriz de confusão, comparando as metricas recolhidas com as regras do utilizador e um ficheiro XLSX, cuja localização deverá ser indicada com o botão <strong><i>xlsx file</i></strong>. Apresentará também as predições corretas e incorretas.
<br>

<br>
<hr>
<strong>Para utilizar este software como Docker Image</strong>:<br>
  1) instalar VcXsrv (https://sourceforge.net/projects/vcxsrv/)<br>
  2) lançar o XLaunch<br>
  3) download da imagem que contém o projeto (docker pull antoniorodrigo92/esgrupo12codesmells)<br>
  4) correr a imagem através do CMD: 
  <ul>
  <li> caso nao pretenda persistencia de regras: docker run -v C:\Users:/C antoniorodrigo92/esgrupo12codesmells </li>
  <li> caso pretenda regras persistentes: docker run -v C:\Users:/C -v regras:/var/lib/regras antoniorodrigo92/esgrupo12codesmells </li>
  </ul>
  <br>
  <strong><u>Nota</u>:</strong> esta imagem docker serve apenas para efeitos demonstrativos, visto carecer de testagem.
