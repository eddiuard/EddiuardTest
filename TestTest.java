package ru.eddi;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class TestTest {

    public static String myRusMoney(Double e1) {
        String[] m0 = {" ", "одна ", "две "};
        String[] m1 = {" ", "один ", "два ", "три ", "четыре ", "пять ", "шесть ", "семь ",  "восемь ",
                 "девять ", "десять ", "одиннадцать ", "двенадцать ", "тринадцать ", "четырнадцать ",
                 "пятнадцать ", "шестнадцать ", "семнадцать ", "восемнадцать ", "девятнадцать "};
        String[] m2 = {" ", " ", "двадцать ",  "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ",
                 "семьдесят ", "восемьдесят ", "девяносто "};
        String[] m3 = {" ", "сто ",  "двести ", "триста ", "четыреста ", "пятьсот ",
                 "шестьсот ", "семьсот ", "восемьсот ","девятьсот "};
        String[] m4 = {" ",
                 "триллион ", "триллиона ", "триллионов ",
                 "миллиард ", "миллиарда ", "миллиардов ",
                 "миллион " , "миллиона " , "миллионов " ,
                 "тысяча "  , "тысячи "   , "тысяч "     ,
                 "рубль "   , "рубля "    , "рублей "    ,
                 "копейка " , "копейки "  , "копеек "};
        String c1,s1; Integer x1,i1;
        c1=String.format("%018.2f", e1);
        c1=c1.substring(0,15)+"0"+c1.substring(16)+" "; s1="";
        for (i1=1; i1<=6; i1++) {
            x1=Integer.valueOf(c1.substring(3*i1-3,3*i1));
            if (x1==0) {
                if (i1==5) s1=s1+"рублей "; if (i1<6) continue;
            }
            if (i1<6) {
                if (x1>99) s1=s1 + m3[x1/100];  x1=x1%100;
                if (x1>19) s1=s1 + m2[x1/ 10];
                if (x1>19) x1=x1%10;
                if (x1>00) {
                    if (x1>2 || i1!=4) s1=s1 + m1[x1]; else s1=s1+m0[x1];
                }
            }  else {
               s1=s1+c1.substring(16,18);
               if (x1>=20) x1=x1%10;
            }
            if (x1==1) x1=1; else {
                if (x1>1 && x1<5) x1=2; else x1=3;
            }
            s1=s1+m4[i1*3-3+x1];
        }
        /*
        if e1<=0.99 then s1:='Ноль '+s1;
        s1[1]:=AnsiUpperCase(s1[1])[1] ;
        if e1<0 then s1:='минус '+s1;
        */
        System.out.println("rusmoney: " + c1 + " = " +s1);
        return s1;
    }

    public static void TestXML1(String fn) {
        try {
            // Создается построитель документа
            // Создается дерево DOM документа из файла
            // Получаем корневой элемент
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("D:/EDDI_JAVA/Books.xml");
            Element root = document.getDocumentElement();

            System.out.println("List of books:");
            // Просматриваем все подэлементы корневого - т.е. книги
            NodeList books = root.getChildNodes();
            for (int i = 0; i < books.getLength(); i++) {
                Node book = books.item(i);
                // Если нода не текст, то это книга - заходим внутрь
                if (book.getNodeType() != Node.TEXT_NODE) {
                    NodeList bookProps = book.getChildNodes();
                    for(int j = 0; j < bookProps.getLength(); j++) {
                        Node bookProp = bookProps.item(j);
                        // Если нода не текст, то это один из параметров книги - печатаем
                        if (bookProp.getNodeType() != Node.TEXT_NODE) {
                            String node2=bookProp.getNodeName();
                            System.out.println(bookProp.getNodeName() + ":" +
                                               bookProp.getChildNodes().item(0).getTextContent());
                            NamedNodeMap attr2 = bookProp.getAttributes();
                            if (attr2.getLength()>0) {
                                System.out.println("attr: " + attr2.item(0).getNodeValue());
                            }
                        }
                    }
                    System.out.println("... next");
                }
            }

            Element tt; Integer i2;
            for (i2=0; i2<8; i2++) {
            Element Person = document.createElement("Person");
            Element ff = document.createElement("ff"); ff.appendChild(document.createTextNode("Фамилия"));
            Element ii = document.createElement("ii"); ii.appendChild(document.createTextNode("Имя"));
            Element oo = document.createElement("oo"); oo.appendChild(document.createTextNode("Отчество"));
            root.appendChild(Person);
            Person.appendChild(ff); Person.appendChild(ii); Person.appendChild(oo);
            Person.setAttribute("id", i2.toString());
            Person.setAttribute("birth_day","День рождения");
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult =  new StreamResult(new File("D:/EDDI_JAVA/Books2.xml"));
            transformer.transform(source, streamResult);
            
        } catch (Exception exc) {
            exc.printStackTrace();
        }

//      } catch (IOException e) {
//          e.printStackTrace();
//      } catch (TransformerConfigurationException e) {
//          e.printStackTrace();
//      } catch (ParserConfigurationException e) {
//          e.printStackTrace();
//      } catch (SAXException e) {
//          e.printStackTrace();
//      } catch (TransformerException e) {
//          e.printStackTrace();
//      }
    }

    public static void main(String[] args) {
    //  System.out.println("Hello, world");
        myRusMoney(12345.67);
        TestXML1("1");
    }
}
