package telibros.gui;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import telibros.model.Autor;
import telibros.model.BasePOJO;
import telibros.model.Data;
import telibros.model.Genero;
import telibros.model.Libro;
import telibros.model.Pais;
import telibros.model.Sexualidad;
import telibros.model.validator.KeyListenerType;
import telibros.model.validator.TextFieldInputValidator;

/**
 *
 * @author igor
 */
public class Visual extends javax.swing.JFrame {
    private Data data;
    private VentanaConTablaSencillo tablaPaises, tablaGeneros;
    private VentanaConTablaDeAutores tablaAutores;
    private VentanaConTablaDeLibros tablaLibros;
    public Visual() {
        try {
            data = new Data();
            tablaPaises = new VentanaConTablaSencillo("Listado completo de países",
                data, data.getListaPaises());
            tablaGeneros = new VentanaConTablaSencillo("Listado completo de generos",
                data, data.getListaGeneros());
            tablaAutores = new VentanaConTablaDeAutores("Listado completo de autores",
                data, data.getListaAutores());
            tablaLibros = new VentanaConTablaDeLibros("Listado completo libros",
                data, data.getListaLibros());
            initComponents();
            setDateSpinners();
            ajustarSpinnersNumericos();
            centrarLaVentanaPrincipal();
            refrescar();
        } catch (IOException ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void centrarLaVentanaPrincipal(){
        this.setLocationRelativeTo(null);
    }
    
    private void ajustarSpinnersNumericos(){
        setSpinnerNumericValidator(spinnerPrecio, 0.f, 1000000000.f, 100.f, 0.01f);
        setSpinnerNumericValidator(spinnerPrecioMin, 0.f, 1000000000.f, 100.f, 0.01f);
        setSpinnerNumericValidator(spinnerPrecioMax, 0.f, 1000000000.f, 100.f, 0.01f);
        setSpinnerNumericValidator(spinnerPaginas, 1, 1000000000, 1, 1);
        setSpinnerNumericValidator(spinnerPaginasMin, 1, 1000000000, 1, 1);
        setSpinnerNumericValidator(spinnerPaginasMax, 1, 1000000000, 1, 1);
        setSpinnerNumericValidator(spinnerEdadMin, 1, 1000000000, 1, 1);
        setSpinnerNumericValidator(spinnerEdadMax, 1, 1000000000, 1, 1);
    }
    
    private void setSpinnerNumericValidator(JSpinner spinner, int min, int max,
            int value, int step){
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner,
            "##########");
        NumberFormatter formatter = (NumberFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);
    }
    
    private void setSpinnerNumericValidator(JSpinner spinner, double min,
            double max, double value, double step){
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor)spinner.getEditor();
        editor.getTextField().addKeyListener(
                new TextFieldInputValidator(KeyListenerType.FLOAT_VALUES_ONLY)
        );   
    }
    
    private void setSpinnerDateEditor(JSpinner spinner){
        Calendar calendar = Calendar.getInstance();
        java.util.Date initialDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -300);
        java.util.Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, +400);
        java.util.Date latestDate = calendar.getTime();
        SpinnerDateModel model = new SpinnerDateModel(initialDate, earliestDate,
            latestDate, Calendar.DAY_OF_MONTH);
        spinner.setModel(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd-MM-yyyy");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);
    }
    
    private void setDateSpinners(){
        setSpinnerDateEditor(spinnerFechaNacimientoReg);
        setSpinnerDateEditor(spinnerFechaMuerteReg);
        setSpinnerDateEditor(spinnerFechaMINLibroBuscar);
        setSpinnerDateEditor(spinnerFechaMAXLibroBuscar);
        setSpinnerDateEditor(spinnerFechaLibroReg);
    }
    
    private void cargarCombos() throws SQLException{
        List<Autor> autores = data.getListaAutores();
        comboAutores.removeAllItems();
        comboAutoresBuscar.removeAllItems();
        for(Autor autor : autores){
            comboAutores.addItem(autor);
            comboAutoresBuscar.addItem(autor);
        }
        
        List<Genero> generos = data.getListaGeneros();
        comboGeneros.removeAllItems();
        comboGenerosBuscar.removeAllItems();
        for(Genero genero : generos){
            comboGeneros.addItem(genero);
            comboGenerosBuscar.addItem(genero);
        }
        
        List<Pais> paises = data.getListaPaises();
        comboPais.removeAllItems();
        comboPaisesBuscarAutor.removeAllItems();
        comboPaisesBuscarLibro.removeAllItems();
        for(Pais pais : paises){
            comboPais.addItem(pais);
            comboPaisesBuscarAutor.addItem(pais);
            comboPaisesBuscarLibro.addItem(pais);
        }
    }
    
    private void refrescar() throws SQLException{
        cargarCombos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrupo = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtNombreAutorReg = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnRegistrarAutor = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        comboPais = new javax.swing.JComboBox();
        rbtnHombre = new javax.swing.JRadioButton();
        rbtnMujer = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnCargarListadoAutores = new javax.swing.JButton();
        spinnerFechaNacimientoReg = new javax.swing.JSpinner();
        spinnerFechaMuerteReg = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarLibro = new javax.swing.JButton();
        btnReseteoLibro = new javax.swing.JButton();
        txtBuscarLibro = new javax.swing.JTextField();
        checkBoxGenero = new javax.swing.JCheckBox();
        checkBoxAutor = new javax.swing.JCheckBox();
        comboGenerosBuscar = new javax.swing.JComboBox();
        comboAutoresBuscar = new javax.swing.JComboBox();
        comboPaisesBuscarLibro = new javax.swing.JComboBox();
        checkBoxPaisBuscarLIbro = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkBoxFechaLibro = new javax.swing.JCheckBox();
        spinnerFechaMINLibroBuscar = new javax.swing.JSpinner();
        spinnerFechaMAXLibroBuscar = new javax.swing.JSpinner();
        spinnerPaginasMax = new javax.swing.JSpinner();
        spinnerPaginasMin = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        checkBoxPaginasLibro = new javax.swing.JCheckBox();
        spinnerPrecioMax = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        spinnerPrecioMin = new javax.swing.JSpinner();
        checkBoxPrecioLibro = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        checkBoxSoloDisponiblesLibros = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        txtNombreLibro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboAutores = new javax.swing.JComboBox();
        btnRegistrarLibro = new javax.swing.JButton();
        comboGeneros = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        spinnerPaginas = new javax.swing.JSpinner();
        spinnerPrecio = new javax.swing.JSpinner();
        checkBoxDisponible = new javax.swing.JCheckBox();
        btnCargarListadoLibros = new javax.swing.JButton();
        spinnerFechaLibroReg = new javax.swing.JSpinner();
        pnlPais = new javax.swing.JPanel();
        txtPais = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnRegistrarPais = new javax.swing.JButton();
        btnCargarListadoPaises = new javax.swing.JButton();
        pnlGenero = new javax.swing.JPanel();
        txtGenero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnRegistrarGenero = new javax.swing.JButton();
        btnCargarListadoGeneros = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtBuscarAutor = new javax.swing.JTextField();
        rbtnMujerBuscar = new javax.swing.JRadioButton();
        rbtnHombreBuscar = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        spinnerEdadMin = new javax.swing.JSpinner();
        spinnerEdadMax = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        comboPaisesBuscarAutor = new javax.swing.JComboBox();
        checkBoxSexoAutor = new javax.swing.JCheckBox();
        checkBoxEdadAutor = new javax.swing.JCheckBox();
        checkBoxAutorPais = new javax.swing.JCheckBox();
        btnReseteoAutor = new javax.swing.JButton();
        btnBuscarAutor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TeLibros");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Autores"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombreAutorReg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombreAutorReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreAutorRegActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombreAutorReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 188, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Nombre autor:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnRegistrarAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarAutor.setText("Regisrar");
        btnRegistrarAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarAutorActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrarAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 135, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("País:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        comboPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        comboPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPaisActionPerformed(evt);
            }
        });
        jPanel1.add(comboPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 188, -1));

        btnGrupo.add(rbtnHombre);
        rbtnHombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnHombre.setSelected(true);
        rbtnHombre.setText("masculino");
        jPanel1.add(rbtnHombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        btnGrupo.add(rbtnMujer);
        rbtnMujer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnMujer.setText("femenino");
        jPanel1.add(rbtnMujer, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Sexualidad:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Fecha de nacimiento:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Fecha de muerte:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        btnCargarListadoAutores.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCargarListadoAutores.setText("Cargar listado");
        btnCargarListadoAutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarListadoAutoresActionPerformed(evt);
            }
        });
        jPanel1.add(btnCargarListadoAutores, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 177, -1));

        spinnerFechaNacimientoReg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(spinnerFechaNacimientoReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 138, -1));

        spinnerFechaMuerteReg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(spinnerFechaMuerteReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 140, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Buscar libros:"));

        btnBuscarLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscarLibro.setText("Buscar");
        btnBuscarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLibroActionPerformed(evt);
            }
        });

        btnReseteoLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReseteoLibro.setText("Reseteo");
        btnReseteoLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReseteoLibroActionPerformed(evt);
            }
        });

        txtBuscarLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        checkBoxGenero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        checkBoxGenero.setText("genero ");

        checkBoxAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        checkBoxAutor.setText("autor");

        comboGenerosBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        comboAutoresBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        comboPaisesBuscarLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        checkBoxPaisBuscarLIbro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        checkBoxPaisBuscarLIbro.setText("país");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("MIN:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("MAX:");

        jLabel13.setText("Nombre:");

        checkBoxFechaLibro.setText("considerar fecha");

        spinnerFechaMINLibroBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        spinnerFechaMAXLibroBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        spinnerPaginasMax.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        spinnerPaginasMin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("MIN:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("MAX:");

        checkBoxPaginasLibro.setText("considerar paginas");

        spinnerPrecioMax.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("MAX:");

        spinnerPrecioMin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        checkBoxPrecioLibro.setText("considerar precio");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("MIN:");

        checkBoxSoloDisponiblesLibros.setText("sólo disponibles");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscarLibro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReseteoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(spinnerFechaMINLibroBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12))
                                .addComponent(checkBoxFechaLibro)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(spinnerPaginasMin, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addGap(114, 114, 114))
                                            .addComponent(checkBoxPrecioLibro, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel22))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(spinnerPrecioMin, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel23))
                                .addComponent(checkBoxPaginasLibro))
                            .addGap(9, 9, 9)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spinnerPaginasMax, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerFechaMAXLibroBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerPrecioMax, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(3, 3, 3))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(164, 164, 164)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboAutoresBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboPaisesBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboGenerosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBoxGenero)
                            .addComponent(jLabel13)
                            .addComponent(checkBoxAutor)
                            .addComponent(checkBoxPaisBuscarLIbro)
                            .addComponent(checkBoxSoloDisponiblesLibros))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxGenero)
                    .addComponent(comboGenerosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBoxAutor)
                    .addComponent(comboAutoresBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBoxPaisBuscarLIbro)
                    .addComponent(comboPaisesBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxFechaLibro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(spinnerFechaMINLibroBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerFechaMAXLibroBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(checkBoxPaginasLibro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(spinnerPaginasMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(spinnerPaginasMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxPrecioLibro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(spinnerPrecioMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(spinnerPrecioMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxSoloDisponiblesLibros)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReseteoLibro)
                    .addComponent(btnBuscarLibro))
                .addGap(6, 6, 6))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Libros"));
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNombreLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nombre libro:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Fecha:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Autor:");

        comboAutores.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnRegistrarLibro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarLibro.setText("Registrar");
        btnRegistrarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarLibroActionPerformed(evt);
            }
        });

        comboGeneros.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Genero:");

        jLabel15.setText("Paginas:");

        jLabel16.setText("Precio:");

        spinnerPaginas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        spinnerPrecio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        checkBoxDisponible.setText("disponible");

        btnCargarListadoLibros.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCargarListadoLibros.setText("Cargar listado");
        btnCargarListadoLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarListadoLibrosActionPerformed(evt);
            }
        });

        spinnerFechaLibroReg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnRegistrarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCargarListadoLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboAutores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerPaginas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerFechaLibroReg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboGeneros, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(checkBoxDisponible)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboGeneros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboAutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spinnerFechaLibroReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(spinnerPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkBoxDisponible)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarLibro)
                    .addComponent(btnCargarListadoLibros))
                .addGap(28, 28, 28))
        );

        pnlPais.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Paises"));

        txtPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaisActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Nombre pais:");

        btnRegistrarPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarPais.setText("Registrar");
        btnRegistrarPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPaisActionPerformed(evt);
            }
        });

        btnCargarListadoPaises.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCargarListadoPaises.setText("Cargar listado");
        btnCargarListadoPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarListadoPaisesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPaisLayout = new javax.swing.GroupLayout(pnlPais);
        pnlPais.setLayout(pnlPaisLayout);
        pnlPaisLayout.setHorizontalGroup(
            pnlPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPais)
                    .addGroup(pnlPaisLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnRegistrarPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCargarListadoPaises, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPaisLayout.setVerticalGroup(
            pnlPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaisLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrarPais)
                .addGap(18, 18, 18)
                .addComponent(btnCargarListadoPaises)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGenero.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Generos"));

        txtGenero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGeneroActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Genero:");

        btnRegistrarGenero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarGenero.setText("Registrar");
        btnRegistrarGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarGeneroActionPerformed(evt);
            }
        });

        btnCargarListadoGeneros.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCargarListadoGeneros.setText("Cargar listado");
        btnCargarListadoGeneros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarListadoGenerosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGeneroLayout = new javax.swing.GroupLayout(pnlGenero);
        pnlGenero.setLayout(pnlGeneroLayout);
        pnlGeneroLayout.setHorizontalGroup(
            pnlGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGenero)
                    .addGroup(pnlGeneroLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnRegistrarGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCargarListadoGeneros, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGeneroLayout.setVerticalGroup(
            pnlGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneroLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrarGenero)
                .addGap(18, 18, 18)
                .addComponent(btnCargarListadoGeneros)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnQuitar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQuitar.setText("Quitar aplicación");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Buscar Autor"));

        jLabel17.setText("Nombre:");

        txtBuscarAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        buttonGroup1.add(rbtnMujerBuscar);
        rbtnMujerBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnMujerBuscar.setText("femenino");

        buttonGroup1.add(rbtnHombreBuscar);
        rbtnHombreBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnHombreBuscar.setSelected(true);
        rbtnHombreBuscar.setText("masculino");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Sexualidad:");

        spinnerEdadMin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        spinnerEdadMax.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel20.setText("MIN:");

        jLabel21.setText("MAX:");

        comboPaisesBuscarAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        checkBoxSexoAutor.setText("considerar sexualidad");

        checkBoxEdadAutor.setText("considerar edad");

        checkBoxAutorPais.setText("considerar país");

        btnReseteoAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReseteoAutor.setText("Reseteo");
        btnReseteoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReseteoAutorActionPerformed(evt);
            }
        });

        btnBuscarAutor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscarAutor.setText("Buscar");
        btnBuscarAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAutorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(41, 41, 41)
                        .addComponent(txtBuscarAutor))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(checkBoxEdadAutor)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinnerEdadMax, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinnerEdadMin, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(rbtnHombreBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnMujerBuscar))
                    .addComponent(checkBoxSexoAutor)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnBuscarAutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkBoxAutorPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReseteoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPaisesBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(checkBoxSexoAutor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnHombreBuscar)
                    .addComponent(rbtnMujerBuscar)
                    .addComponent(jLabel18))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerEdadMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerEdadMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(checkBoxEdadAutor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBoxAutorPais)
                    .addComponent(comboPaisesBuscarAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarAutor)
                    .addComponent(btnReseteoAutor))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlGenero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreAutorRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreAutorRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAutorRegActionPerformed

    private void btnRegistrarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarLibroActionPerformed
        Libro lib = new Libro();
        lib.setNombre(txtNombreLibro.getText());
        lib.setAutor(((Autor)comboAutores.getSelectedItem()).getId());
        lib.setGenero(((BasePOJO)comboGeneros.getSelectedItem()).getId());
        java.util.Date fecha = (java.util.Date)spinnerFechaLibroReg.getValue();
        java.sql.Date fecha_sql = new java.sql.Date(fecha.getTime());
        lib.setFecha(fecha_sql);
        lib.setPaginas((int)spinnerPaginas.getValue());
        lib.setPrecio((double)spinnerPrecio.getValue());
        lib.setDisponible(checkBoxDisponible.isSelected());
        try {
            data.meterLibro(lib);
            JOptionPane.showMessageDialog(
                null,
                "El libro nuevo fué registrado con éxito",
                "Message",
                JOptionPane.INFORMATION_MESSAGE
            );
            limpiarLibroRegistrarFormulario();
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarLibroActionPerformed

    private void limpiarLibroRegistrarFormulario(){
        txtNombreLibro.setText(null);
        if(comboAutores.getItemCount() > 0){
            comboAutores.setSelectedIndex(0);
        }
        if(comboGeneros.getItemCount() > 0){
            comboGeneros.setSelectedIndex(0);
        }
        Calendar cal = Calendar.getInstance();
        java.util.Date fechaActual = cal.getTime();
        spinnerFechaLibroReg.setValue(fechaActual);
        spinnerPaginas.setValue(1);
        spinnerPrecio.setValue(100.f);
        checkBoxDisponible.setSelected(true);
    }
    
    private void btnRegistrarAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAutorActionPerformed
        String nombre = txtNombreAutorReg.getText();
        Sexualidad sexo = rbtnHombre.isSelected() ? Sexualidad.M : Sexualidad.F;
        java.util.Date fecha_n_util = (java.util.Date)spinnerFechaNacimientoReg.getValue();
        Date fecha_n_sql = new Date(fecha_n_util.getTime());
        java.util.Date fecha_m_util = (java.util.Date)spinnerFechaMuerteReg.getValue();
        Date fecha_m_sql = new Date(fecha_m_util.getTime());
        int id_pais = ((BasePOJO)comboPais.getSelectedItem()).getId();
        Autor autor = new Autor(nombre, sexo, fecha_n_sql, fecha_m_sql, id_pais);
        try {
            data.meterAutor(autor);
            JOptionPane.showMessageDialog(
                null,
                "El autor nuevo fué registrado con éxito",
                "Message",
                JOptionPane.INFORMATION_MESSAGE
            );
            limpiarAutorRegFormulario();
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarAutorActionPerformed
    
    private void limpiarAutorRegFormulario(){
        txtNombreAutorReg.setText(null);
        rbtnHombre.setSelected(true);
        Calendar cal = Calendar.getInstance();
        java.util.Date fechaActual = cal.getTime();
        spinnerFechaNacimientoReg.setValue(fechaActual);
        spinnerFechaMuerteReg.setValue(fechaActual);
        if(comboPais.getItemCount() > 0){
            comboPais.setSelectedIndex(0);
        }
    }
    
    private void txtPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaisActionPerformed

    private void btnRegistrarPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPaisActionPerformed
        try {
            String nombre = txtPais.getText();
            Pais pais = new Pais(nombre);
            System.out.println("Cantidad de paises antes de insert = " + data.countPaises());
            data.meterPais(pais);
            JOptionPane.showMessageDialog(
                null,
                "El país nuevo fué registrado con éxito",
                "Message",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("Cantidad de paises despues de insert = " + data.countPaises());
            //tablaPaises.setLista(data.getListaPaises());
            txtPais.setText(null);
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarPaisActionPerformed

    private void txtGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGeneroActionPerformed

    private void btnRegistrarGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarGeneroActionPerformed
        try {
            String nombre = txtGenero.getText();
            Genero genero = new Genero(nombre);
            System.out.println("Cantidad de generos antes de insert = " + data.countGeneros());
            data.meterGenero(genero);
            JOptionPane.showMessageDialog(
                null,
                "El genero nuevo fué registrado con éxito",
                "Message",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("Cantidad de generos despues de insert = " + data.countGeneros());
            txtGenero.setText(null);
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnRegistrarGeneroActionPerformed

    private void btnCargarListadoPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarListadoPaisesActionPerformed
        try {
            tablaPaises.setLista(data.getListaPaises());
            if(!tablaPaises.isVisible()){
                tablaPaises.setVisible(true);
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCargarListadoPaisesActionPerformed

    private void btnCargarListadoAutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarListadoAutoresActionPerformed
        try {
            tablaAutores.actualizar(data.getListaAutores());
            if(!tablaAutores.isVisible()){
                tablaAutores.setVisible(true);
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCargarListadoAutoresActionPerformed

    private void btnCargarListadoGenerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarListadoGenerosActionPerformed
        try {
            tablaGeneros.setLista(data.getListaGeneros());
            if(!tablaGeneros.isVisible()){
                tablaGeneros.setVisible(true);
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCargarListadoGenerosActionPerformed

    private void comboPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnCargarListadoLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarListadoLibrosActionPerformed
        try {
            tablaLibros.actualizar(data.getListaLibros());
            if(!tablaLibros.isVisible()){
                tablaLibros.setVisible(true);
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCargarListadoLibrosActionPerformed

    private void btnBuscarAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAutorActionPerformed
        try {
            String regExp = txtBuscarAutor.getText();
            boolean considerarSexo = checkBoxSexoAutor.isSelected();
            Sexualidad sexo;
            if(rbtnHombreBuscar.isSelected()){
                sexo = Sexualidad.M;
            } else {
                sexo = Sexualidad.F;
            }
            boolean considerarEdad = checkBoxEdadAutor.isSelected();
            int edadMin = (int) spinnerEdadMin.getValue();
            int edadMax = (int) spinnerEdadMax.getValue();
            boolean considerarPais = checkBoxAutorPais.isSelected();
            Pais pais = (Pais)comboPaisesBuscarAutor.getSelectedItem();
            int id_pais = pais.getId();
            List<Autor> lista = data.buscarAutores(
                    regExp,
                    considerarSexo,
                    sexo,
                    considerarEdad,
                    edadMin,
                    edadMax,
                    considerarPais,
                    id_pais
            );
            tablaAutores.actualizar(lista);
            if(!tablaAutores.isVisible()){
                tablaAutores.setVisible(true);
            }
            if(lista.isEmpty()){
                JOptionPane.showMessageDialog(
                null,
                "Los autores con parametros mencionados no se encontraron.",
                "No tenemos resultados (",
                JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                null,
                lista.size() + " autores se encontraron.",
                "Tenemos " + lista.size() + "resultados",
                JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarAutorActionPerformed

    private void btnReseteoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReseteoAutorActionPerformed
        txtBuscarAutor.setText(null);
        checkBoxSexoAutor.setSelected(false);
        rbtnHombreBuscar.setSelected(true);
        checkBoxEdadAutor.setSelected(false);
        spinnerEdadMin.setValue(0);
        spinnerEdadMax.setValue(100);
        checkBoxAutorPais.setSelected(false);
        if(comboPaisesBuscarAutor.getItemCount() > 0){
            comboPaisesBuscarAutor.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnReseteoAutorActionPerformed

    private void btnBuscarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLibroActionPerformed
        try {
            // TODO add your handling code here:
            String regExp = txtBuscarLibro.getText();
            boolean considerarGenero = checkBoxGenero.isSelected();
            Genero genero = (Genero)comboGenerosBuscar.getSelectedItem();
            int id_genero = genero.getId();
            boolean considerarAutor = checkBoxAutor.isSelected();
            Autor autor = (Autor)comboAutoresBuscar.getSelectedItem();
            int id_autor = autor.getId();
            boolean considerarPais = checkBoxPaisBuscarLIbro.isSelected();
            Pais pais = (Pais)comboPaisesBuscarLibro.getSelectedItem();
            int id_pais = pais.getId();
            boolean considerarFecha = checkBoxFechaLibro.isSelected();
            java.util.Date fechaMin = (java.util.Date) spinnerFechaMINLibroBuscar.getValue();
            java.sql.Date fechaMinSQL = new java.sql.Date(fechaMin.getTime());
            java.util.Date fechaMax = (java.util.Date) spinnerFechaMAXLibroBuscar.getValue();
            java.sql.Date fechaMaxSQL = new java.sql.Date(fechaMax.getTime());
            boolean considerarPaginas = checkBoxPaginasLibro.isSelected();
            int paginasMin = (int) spinnerPaginasMin.getValue();
            int paginasMax = (int) spinnerPaginasMax.getValue();
            boolean considerarPrecio = checkBoxPrecioLibro.isSelected();
            double precioMin = (double) spinnerPrecioMin.getValue();
            double precioMax = (double) spinnerPrecioMax.getValue();
            boolean soloDisponibles = checkBoxSoloDisponiblesLibros.isSelected();
            List<Libro> lista = data.buscarLibros(
                    regExp,
                    considerarGenero,
                    id_genero,
                    considerarAutor,
                    id_autor,
                    considerarPais,
                    id_pais,
                    considerarFecha,
                    fechaMinSQL,
                    fechaMaxSQL,
                    considerarPaginas,
                    paginasMin,
                    paginasMax,
                    considerarPrecio,
                    precioMin,
                    precioMax,
                    soloDisponibles
            );
            tablaLibros.actualizar(lista);
            if(!tablaLibros.isVisible()){
                tablaLibros.setVisible(true);
            }
            if(lista.isEmpty()){
                JOptionPane.showMessageDialog(
                null,
                "Los libros con parametros mencionados no se encontraron.",
                "No tenemos resultados (",
                JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                null,
                lista.size() + " libros se encontraron.",
                "Tenemos " + lista.size() + "resultados",
                JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException ex) {
            mostrarSQLErrorMensaje(ex);
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarLibroActionPerformed

    private void btnReseteoLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReseteoLibroActionPerformed
        txtBuscarLibro.setText(null);
        checkBoxGenero.setSelected(false);
        if(comboGenerosBuscar.getItemCount() > 0){    
            comboGenerosBuscar.setSelectedIndex(0);
        }
        checkBoxAutor.setSelected(false);
        if(comboAutoresBuscar.getItemCount() > 0){
            comboAutoresBuscar.setSelectedItem(0);
        }
        checkBoxPaisBuscarLIbro.setSelected(false);
        if(comboPaisesBuscarLibro.getItemCount() > 0){
            comboPaisesBuscarLibro.setSelectedItem(0);
        }
        checkBoxFechaLibro.setSelected(false);
        Calendar cal = Calendar.getInstance();
        java.util.Date fechaActual = cal.getTime();
        spinnerFechaMINLibroBuscar.setValue(fechaActual);
        spinnerFechaMAXLibroBuscar.setValue(fechaActual);
        checkBoxPaginasLibro.setSelected(false);
        spinnerPaginasMin.setValue(0);
        spinnerPaginasMax.setValue(0);
        checkBoxPrecioLibro.setSelected(false);
        spinnerPrecioMin.setValue(0.f);
        spinnerPrecioMax.setValue(100.f);
        checkBoxSoloDisponiblesLibros.setSelected(false);
    }//GEN-LAST:event_btnReseteoLibroActionPerformed

    private void mostrarSQLErrorMensaje(SQLException ex){
        JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "DataBase error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarAutor;
    private javax.swing.JButton btnBuscarLibro;
    private javax.swing.JButton btnCargarListadoAutores;
    private javax.swing.JButton btnCargarListadoGeneros;
    private javax.swing.JButton btnCargarListadoLibros;
    private javax.swing.JButton btnCargarListadoPaises;
    private javax.swing.ButtonGroup btnGrupo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegistrarAutor;
    private javax.swing.JButton btnRegistrarGenero;
    private javax.swing.JButton btnRegistrarLibro;
    private javax.swing.JButton btnRegistrarPais;
    private javax.swing.JButton btnReseteoAutor;
    private javax.swing.JButton btnReseteoLibro;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkBoxAutor;
    private javax.swing.JCheckBox checkBoxAutorPais;
    private javax.swing.JCheckBox checkBoxDisponible;
    private javax.swing.JCheckBox checkBoxEdadAutor;
    private javax.swing.JCheckBox checkBoxFechaLibro;
    private javax.swing.JCheckBox checkBoxGenero;
    private javax.swing.JCheckBox checkBoxPaginasLibro;
    private javax.swing.JCheckBox checkBoxPaisBuscarLIbro;
    private javax.swing.JCheckBox checkBoxPrecioLibro;
    private javax.swing.JCheckBox checkBoxSexoAutor;
    private javax.swing.JCheckBox checkBoxSoloDisponiblesLibros;
    private javax.swing.JComboBox comboAutores;
    private javax.swing.JComboBox comboAutoresBuscar;
    private javax.swing.JComboBox comboGeneros;
    private javax.swing.JComboBox comboGenerosBuscar;
    private javax.swing.JComboBox comboPais;
    private javax.swing.JComboBox comboPaisesBuscarAutor;
    private javax.swing.JComboBox comboPaisesBuscarLibro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel pnlGenero;
    private javax.swing.JPanel pnlPais;
    private javax.swing.JRadioButton rbtnHombre;
    private javax.swing.JRadioButton rbtnHombreBuscar;
    private javax.swing.JRadioButton rbtnMujer;
    private javax.swing.JRadioButton rbtnMujerBuscar;
    private javax.swing.JSpinner spinnerEdadMax;
    private javax.swing.JSpinner spinnerEdadMin;
    private javax.swing.JSpinner spinnerFechaLibroReg;
    private javax.swing.JSpinner spinnerFechaMAXLibroBuscar;
    private javax.swing.JSpinner spinnerFechaMINLibroBuscar;
    private javax.swing.JSpinner spinnerFechaMuerteReg;
    private javax.swing.JSpinner spinnerFechaNacimientoReg;
    private javax.swing.JSpinner spinnerPaginas;
    private javax.swing.JSpinner spinnerPaginasMax;
    private javax.swing.JSpinner spinnerPaginasMin;
    private javax.swing.JSpinner spinnerPrecio;
    private javax.swing.JSpinner spinnerPrecioMax;
    private javax.swing.JSpinner spinnerPrecioMin;
    private javax.swing.JTextField txtBuscarAutor;
    private javax.swing.JTextField txtBuscarLibro;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtNombreAutorReg;
    private javax.swing.JTextField txtNombreLibro;
    private javax.swing.JTextField txtPais;
    // End of variables declaration//GEN-END:variables
}
