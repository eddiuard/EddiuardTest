
   es$.SortFunc = function(a_sort, a_cntx) { 
   // ("f_dat2:D:+:g, f_bika:C:+:g, f_nlsa:C:+:g, f_ssr:N:-:-", null)
      let PP,FF;
      PP=a_sort.split(" ").join("").split(",").map(p1 => {
         let p2=p1.split(":"), p3={};
         p3.prm= p2[0];  
         p3.dtp= p2[1];
         p3.ord=(p2[2].toLowerCase()=="1" ? -1 : 1);
         p3.grp=(p2[3].toLowerCase()=="g" ?  1 : 0);
         return p3;
      }) ; 
      FF = function(a1,a2) { 
         let prm,dtp,ord, x1,x2, v1,v2, i1,r1=0;
         x1=(a_cntx ? a_cntx[a1] : a1);
         x2=(a_cntx ? a_cntx[a2] : a2);
         for (i1=0; i1<PP.length; i1++) {
            ({prm,dtp,ord} = PP[i1]);  v1=x1[prm];  v2=x2[prm];
            if (dtp=="D") { v1=v1.substr(6,4)+v1.substr(3,2)+v1.substr(0,2);
                            v2=v2.substr(6,4)+v2.substr(3,2)+v2.substr(0,2); }
            if (dtp=="N") { v1=v1.split(",").join(".")-0;  
                            v2=v2.split(",").join(".")-0; }
            if (v1<v2) { r1=-ord; break; };
            if (v1>v2) { r1= ord; break; };
         }
         return r1;
      };
      return {PP:PP, FF:FF};
   }
