/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocalculadora;

/**
 *
 * @author rdo12
 */
public class Calculadora {
    
    //este método revisa si el carácter es operador o no
    public boolean isOperator(char a){
        boolean resp=true;
        
        switch(a){
            case'+':
            case'-':
            case'/':
            case '*':
            case'(':
            case ')':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    public boolean isOperando(char a){
        boolean resp=true;
        
        switch(a){
            case'1':
            case'2':
            case'3':
            case'4':
            case'5':
            case'6':
            case'7':
            case'8':
            case'9':
            case'0':
            case'.':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    //verifica parentesis
    public boolean verificaParentesis(String algo) {

        boolean resp = true;
        PilaADT<Character> pila = new Pila();

        int i = 0;

        while (i < algo.length() && resp) {
            if (algo.charAt(i) == '(') {
                pila.push(algo.charAt(i));
            } else {
                if (algo.charAt(i) == ')') {
                    if (!pila.isEmpty()) {
                        pila.pop();
                    } else {
                        resp = false;
                    }
                }
                i++;
            }
            if (resp && pila.isEmpty()) { //si estan balanceados continua
                //despues verifica que no haya operadores juntos
                i = 0;
                while (i < algo.length() && resp) {
                    if (algo.charAt(i) == '+' || algo.charAt(i) == '-' || algo.charAt(i) == '*' || algo.charAt(i) == '/' || algo.charAt(i) == '.') {
                        if (!pila.isEmpty()) {
                            resp = false;
                        } else {
                            pila.push(algo.charAt(i));
                        }
                    } else {
                        if (algo.charAt(i) != '(' && algo.charAt(i) != ')') { //ignora los parentesis
                            if (algo.charAt(i) == '0' && algo.charAt(i - 1) == '/') {
                                resp = false;
                            } else {
                                try {
                                    pila.pop();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
        if (resp && pila.isEmpty()) {
            resp = true;
        }
        return resp;
    }
    
    
    public boolean jerarquiaOperandos(char operando, char tope) {
        boolean resp = true;
        switch (operando) {
            case '+':
                if (tope == '+') {
                    resp = false;
                }
                break;
            case '*':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '/':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '^':
                resp = false;
                break;
            default:
                resp = false;
                break;
        }
        return resp;
    }
    
    // Infija a posfija 
      public static String InfijaPostfija(String infija){
        String res=" ";
        
        String[] arre=infija.split("\\s");
        int i=0;
        ArrayStack<String> signos=new ArrayStack();
        ArrayStack<String> posfija=new ArrayStack();
        while(i<arre.length){
            switch(arre[i]){
                case"(": 
                    signos.push(arre[i]);
                    break;
                case")": 
                    while(!signos.isEmpty() && !signos.peek().equals("(")){
                       
                        posfija.push(signos.pop());  
                    }
                    signos.pop();
                    break;
                case"x":
                    jerarquiaOperaciones(posfija,signos,"x");
                    signos.push(arre[i]);
                    break;
                case"÷":
                    jerarquiaOperaciones(posfija,signos,"÷");
                    signos.push(arre[i]);
                    break;
                case"+":
                    jerarquiaOperaciones(posfija,signos,"+");
                    signos.push(arre[i]);
                    break;
                case"-":
                    jerarquiaOperaciones(posfija,signos,"-");
                    signos.push(arre[i]);
                    break;   
                default:
                    posfija.push(arre[i]);
                    break;
            }
            i++;
        }
        
        while(!signos.isEmpty())
            posfija.push(signos.pop());
        while(!posfija.isEmpty())
            signos.push(posfija.pop());
        while(!signos.isEmpty())
            res= res+signos.pop()+" ";

        return res;    
    }
    
    
    // verifica validez 
        public static boolean revisaSignos(String infija){
        boolean res=true;
        boolean prim=false, sec=false;
        String[] arre=infija.split("\\s");
        int i=1;
        int x=0;
        int c=arre.length-1;
        
     
        if((arre[x].equals("+") || arre[x].equals("-") || arre[x].equals("x") || arre[x].equals("÷")) ||
            (arre[c].equals("+") || arre[c].equals("-") || arre[c].equals("x") || arre[c].equals("÷")))
            res=false;
        else{
           
            while(i<arre.length && !prim && !sec){
                
                if(arre[x].equals("+") || arre[x].equals("-") || arre[x].equals("x") || arre[x].equals("÷"))
                    prim=true;
                if(arre[i].equals("+") || arre[i].equals("-") || arre[i].equals("x") || arre[i].equals("÷"))
                    sec=true;
                
                if(prim==false || sec==false){
                    prim=false;
                    sec=false;
                }
                i++;
                x++;
            }        
            if(i<arre.length)
                res=false;
        }
        return res;
    }
    
    
    //Evaluar posfija
       public static double evaluaPosfija(String posfija){
       double res;
       double aux1,aux2;
       String[] arre=posfija.split("\\s");
       ArrayStack<String> ecuacion=new ArrayStack();
       ArrayStack<String> ecuacion1=new ArrayStack();
      
        for (String arre1 : arre) {
            ecuacion.push(arre1);
        }
      
       while(!ecuacion.isEmpty()){
           ecuacion1.push(ecuacion.pop());
       }
       
       while(!ecuacion1.isEmpty()){
           

           if(!ecuacion1.peek().equals("+") && !ecuacion1.peek().equals("-") && !ecuacion1.peek().equals("x") &&
                   !ecuacion1.peek().equals("÷")){
              ecuacion.push(ecuacion1.pop());
           }
          
           else{
               aux1=Double.parseDouble(ecuacion.pop());
               
               aux2=Double.parseDouble(ecuacion.pop());
               
                switch(ecuacion1.peek()){
                    
                    case "+":
                        res=aux1+aux2;
                        ecuacion.push(res+"");
                        break;
                    case "-":
                        res=aux2-aux1;
                        ecuacion.push(res+"");
                        break;
                     case "x":
                        res=aux1*aux2;
                        ecuacion.push(res+"");
                        break;
                     case "÷":
                        res=aux2/aux1;
                        ecuacion.push(res+"");
                        break;
                }
                ecuacion1.pop();
           }
           
       }
       res=Double.parseDouble(ecuacion.pop());
       return res;
   }
  
        public void enableOperators(boolean resp){
        btnSum.setEnabled(resp);
        btnMult.setEnabled(resp);
        btnDiv.setEnabled(resp);
        btnRest.setEnabled(resp);
        btnPunto.setEnabled(resp);
    }
    
    
    
    
    
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txt1 = new javax.swing.JTextField();
        btnIgual = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnParI = new javax.swing.JButton();
        btnParD = new javax.swing.JButton();
        btnSum = new javax.swing.JButton();
        btnRest = new javax.swing.JButton();
        btnMult = new javax.swing.JButton();
        btnDiv = new javax.swing.JButton();
        btnPunto = new javax.swing.JButton();
        btnSigNeg = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel3.setOpaque(false);

        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });

        btnIgual.setBackground(new java.awt.Color(255, 255, 255));
        btnIgual.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnIgual.setText("=");
        btnIgual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIgualActionPerformed(evt);
            }
        });

        jPanel1.setOpaque(false);

        btn7.setBackground(new java.awt.Color(25, 25, 112));
        btn7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn7.setForeground(new java.awt.Color(255, 255, 255));
        btn7.setText("7");
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn8.setBackground(new java.awt.Color(25, 25, 112));
        btn8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn8.setForeground(new java.awt.Color(255, 255, 255));
        btn8.setText("8");
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn9.setBackground(new java.awt.Color(25, 25, 112));
        btn9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn9.setForeground(new java.awt.Color(255, 255, 255));
        btn9.setText("9");
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btn4.setBackground(new java.awt.Color(25, 25, 112));
        btn4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn4.setForeground(new java.awt.Color(255, 255, 255));
        btn4.setText("4");
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn5.setBackground(new java.awt.Color(25, 25, 112));
        btn5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn5.setForeground(new java.awt.Color(255, 255, 255));
        btn5.setText("5");
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn6.setBackground(new java.awt.Color(25, 25, 112));
        btn6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn6.setForeground(new java.awt.Color(255, 255, 255));
        btn6.setText("6");
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btn1.setBackground(new java.awt.Color(25, 25, 112));
        btn1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn1.setForeground(new java.awt.Color(255, 255, 255));
        btn1.setText("1");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setBackground(new java.awt.Color(25, 25, 112));
        btn2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn2.setForeground(new java.awt.Color(255, 255, 255));
        btn2.setText("2");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setBackground(new java.awt.Color(25, 25, 112));
        btn3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn3.setForeground(new java.awt.Color(255, 255, 255));
        btn3.setText("3");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btn0.setBackground(new java.awt.Color(25, 25, 112));
        btn0.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn0.setForeground(new java.awt.Color(255, 255, 255));
        btn0.setText("0");
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btnClean.setBackground(new java.awt.Color(255, 255, 255));
        btnClean.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnClean.setForeground(new java.awt.Color(255, 255, 255));
        btnClean.setText("DEL");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClean, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setOpaque(false);

        btnParI.setBackground(new java.awt.Color(25, 25, 112));
        btnParI.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnParI.setForeground(new java.awt.Color(255, 255, 255));
        btnParI.setText("(");
        btnParI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParIActionPerformed(evt);
            }
        });

        btnParD.setBackground(new java.awt.Color(25, 25, 112));
        btnParD.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnParD.setForeground(new java.awt.Color(255, 255, 255));
        btnParD.setText(")");
        btnParD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParDActionPerformed(evt);
            }
        });

        btnSum.setBackground(new java.awt.Color(25, 25, 112));
        btnSum.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnSum.setForeground(new java.awt.Color(255, 255, 255));
        btnSum.setText("+");
        btnSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumActionPerformed(evt);
            }
        });

        btnRest.setBackground(new java.awt.Color(25, 25, 112));
        btnRest.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnRest.setForeground(new java.awt.Color(255, 255, 255));
        btnRest.setText("-");
        btnRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestActionPerformed(evt);
            }
        });

        btnMult.setBackground(new java.awt.Color(25, 25, 112));
        btnMult.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnMult.setForeground(new java.awt.Color(255, 255, 255));
        btnMult.setText("x");
        btnMult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultActionPerformed(evt);
            }
        });

        btnDiv.setBackground(new java.awt.Color(25, 25, 112));
        btnDiv.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnDiv.setForeground(new java.awt.Color(255, 255, 255));
        btnDiv.setText("÷");
        btnDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivActionPerformed(evt);
            }
        });

        btnPunto.setBackground(new java.awt.Color(25, 25, 112));
        btnPunto.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnPunto.setForeground(new java.awt.Color(255, 255, 255));
        btnPunto.setText(".");
        btnPunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntoActionPerformed(evt);
            }
        });

        btnSigNeg.setBackground(new java.awt.Color(25, 25, 112));
        btnSigNeg.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSigNeg.setForeground(new java.awt.Color(255, 255, 255));
        btnSigNeg.setText("(-)");
        btnSigNeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigNegActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnParI, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnParD, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSum, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRest, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnMult, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPunto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSigNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnParI, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnParD, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSum, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRest, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMult, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPunto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSigNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(btnIgual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(40, 60, 440, 340);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-220, -40, 900, 530);

        pack();
    }// </editor-fold>                        

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        
    }                                    

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"1");   
    }                                    

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"2");
    }                                    

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {                                     
       enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"3");
    }                                    

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"4");
    }                                    

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"5");
    }                                    

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"6");
    }                                    

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"7");
    }                                    

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"8");
    }                                    

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"9");
    }                                    

    private void btnRestActionPerformed(java.awt.event.ActionEvent evt) {                                        
        
        enableOperators(false);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+" - ");
    }                                       

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {                                     
       enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"0"); 
    }                                    

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {                                         
        txt1.setText("");
    }                                        

    private void btnSumActionPerformed(java.awt.event.ActionEvent evt) {                                       
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" + ");
    }                                      

    private void btnMultActionPerformed(java.awt.event.ActionEvent evt) {                                        
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" x ");
    }                                       

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {                                       
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" ÷ ");
    }                                      

    private void btnParIActionPerformed(java.awt.event.ActionEvent evt) {                                        
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+"( ");
    }                                       

    private void btnParDActionPerformed(java.awt.event.ActionEvent evt) {                                        
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" )");
    }                                       

    private void btnPuntoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+".");
    }                                        

    private void btnSigNegActionPerformed(java.awt.event.ActionEvent evt) {                                          
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+"-");
    }                                         

    private void btnIgualActionPerformed(java.awt.event.ActionEvent evt) {                                         
    if(analisisParentesis(txt1.getText()))
            if(revisaSignos(txt1.getText())){
                try{
                    ecuacion =InfijaPostfija(txt1.getText());
                    num=resuelveEcuacion(ecuacion);
                    txt1.setText(num+"");
                }
                catch(Exception e){
                txt1.setText("Error en sintaxis o la ecuacion esta vacia");
            }
            }
            else
                txt1.setText("Error en balanceo de signos");
        else
            txt1.setText("Error en balanceo de parentesis");       
    }                                        

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnIgual;
    private javax.swing.JButton btnMult;
    private javax.swing.JButton btnParD;
    private javax.swing.JButton btnParI;
    private javax.swing.JButton btnPunto;
    private javax.swing.JButton btnRest;
    private javax.swing.JButton btnSigNeg;
    private javax.swing.JButton btnSum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txt1;
    // End of variables declaration                   
}
    
    
    
    
    
    
    
}
