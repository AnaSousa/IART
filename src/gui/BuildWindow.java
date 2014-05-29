package gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.ProgramData;

@SuppressWarnings("serial")
public class BuildWindow extends JFrame {

	private JPanel contentPane;
	private BuildPanel panel;
	private JLabel lblVertical;
	private JLabel lblHorizontal;
	private JSlider sliderV;
	private JSlider sliderH;
	private JRadioButton rdbtnPetrolStation;
	private JRadioButton rdbtnGarbageDeposit;
	private JRadioButton rdbtnGarbageBin;
	private JRadioButton rdbtnStreet;
	private JRadioButton rdbtnInitialPosition;
	private JLabel lblMapsScale;
	private JTextField textGarbage;
	private JLabel lblTruckCapacity;
	private JTextField textScale;
	private JLabel lblGarbage;
	private JLabel lblGas;
	private JTextField textGas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuildWindow frame = new BuildWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BuildWindow() {
		setTitle("Map Builder");
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\truckIcon.png"));
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				Component c = (Component) arg0.getSource();

				panel.setSize(c.getSize().width - 111, c.getSize().height);
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new BuildPanel();
		panel.setBounds(158, 11, 416, 550);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel);

		JPanel toolsPanel = new JPanel();
		toolsPanel.setBounds(10, 11, 140, 550);
		contentPane.add(toolsPanel);
		toolsPanel.setLayout(null);

		JLabel lblSize = new JLabel("Size");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSize.setBounds(10, 11, 46, 14);
		toolsPanel.add(lblSize);

		lblVertical = new JLabel("10");
		lblVertical.setHorizontalAlignment(SwingConstants.CENTER);
		lblVertical.setBounds(31, 42, 29, 14);
		toolsPanel.add(lblVertical);

		lblHorizontal = new JLabel("10");
		lblHorizontal.setHorizontalAlignment(SwingConstants.CENTER);
		lblHorizontal.setBounds(72, 79, 28, 14);
		toolsPanel.add(lblHorizontal);


		sliderH = new JSlider();
		sliderH.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblHorizontal.setText(Integer.toString(sliderH.getValue()));
			}
		});
		sliderH.setValue(10);
		sliderH.setBounds(16, 104, 84, 23);
		sliderH.setMinimum(1);
		sliderH.setMaximum(69);
		toolsPanel.add(sliderH);



		sliderV = new JSlider();
		sliderV.setValue(10);
		sliderV.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblVertical.setText(Integer.toString(sliderV.getValue()));
			}
		});
		sliderV.setOrientation(SwingConstants.VERTICAL);
		sliderV.setBounds(0, 36, 37, 70);
		sliderV.setMinimum(1);
		sliderV.setMaximum(40);
		toolsPanel.add(sliderV);

		Button button = new Button("Update");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setMapSize(sliderH.getValue(), sliderV.getValue());
			}
		});
		button.setBounds(26, 133, 70, 22);
		toolsPanel.add(button);

		JLabel lblObjects = new JLabel("Objects");
		lblObjects.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObjects.setBounds(10, 184, 70, 14);
		toolsPanel.add(lblObjects);

		rdbtnStreet = new JRadioButton("Street");
		rdbtnStreet.setSelected(true);
		rdbtnStreet.setBounds(6, 205, 109, 23);
		rdbtnStreet.addItemListener(new HandlerIcon(BuildPanel.STREET));
		toolsPanel.add(rdbtnStreet);

		rdbtnGarbageBin = new JRadioButton("Garbage bin");
		rdbtnGarbageBin.setBounds(6, 231, 109, 23);
		rdbtnGarbageBin.addItemListener(new HandlerIcon(BuildPanel.GARBAGE_BIN));
		toolsPanel.add(rdbtnGarbageBin);

		rdbtnPetrolStation = new JRadioButton("Gas Station");
		rdbtnPetrolStation.setBounds(6, 257, 109, 23);
		rdbtnPetrolStation.addItemListener(new HandlerIcon(BuildPanel.GAS_STATION));
		toolsPanel.add(rdbtnPetrolStation);

		rdbtnGarbageDeposit = new JRadioButton("Garbage deposit");
		rdbtnGarbageDeposit.setBounds(6, 283, 123, 23);
		rdbtnGarbageDeposit.addItemListener(new HandlerIcon(BuildPanel.GARBAGE_DEPOSIT));
		toolsPanel.add(rdbtnGarbageDeposit);

		rdbtnInitialPosition = new JRadioButton("Initial position");
		rdbtnInitialPosition.setBounds(6, 309, 123, 23);
		rdbtnInitialPosition.addItemListener(new HandlerIcon(BuildPanel.INITIAL_POSITION));
		toolsPanel.add(rdbtnInitialPosition);

		ButtonGroup options = new ButtonGroup();
		options.add(rdbtnGarbageBin);
		options.add(rdbtnStreet);
		options.add(rdbtnGarbageDeposit);
		options.add(rdbtnPetrolStation);
		options.add(rdbtnInitialPosition);

		final JButton btnCalcular = new JButton("Calculate");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				btnCalcular.setEnabled(false);
				try{
					ProgramData.deleteInstance();

					ProgramData.getInstance().setMultiple(Integer.parseInt(textScale.getText()));
					ProgramData.getInstance().getTruck().setCapacity(Integer.parseInt(textGarbage.getText()));
					ProgramData.getInstance().getTruck().setFuel(Integer.parseInt(textGas.getText()));

					if(ProgramData.getInstance().getTruck().getCapacity() < 100)
						JOptionPane.showMessageDialog(null,"Minimum garbage capacity is 100!", "ERROR",JOptionPane.ERROR_MESSAGE);
					panel.startAlgorithm();

					System.out.println("Ready to go!");
					MainWindow window = new MainWindow();
					window.frmAAlgorithmWaste.setVisible(true);
					dispose();
				}
				catch(Exception e) {
					if(e.getMessage() != null) {
						if(e.getMessage().equals("fuelError"))
							JOptionPane.showMessageDialog(null,"It isn't enough gas!", "ERROR",JOptionPane.ERROR_MESSAGE);
						else if (e.getMessage().equals("1"))
							JOptionPane.showMessageDialog(null,"The map needs an initial node, a gas station and a dump!", "ERROR",JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(null,"You need to fill all fields!", "ERROR",JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null,"You need to fill all fields!", "ERROR",JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();
					System.out.println("ERROR");
					btnCalcular.setEnabled(true);
				}

			}
		});
		btnCalcular.setBounds(22, 456, 93, 23);
		toolsPanel.add(btnCalcular);

		JLabel lblLbAdd = new JLabel("LB - add element");
		lblLbAdd.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblLbAdd.setBounds(0, 511, 102, 14);
		toolsPanel.add(lblLbAdd);

		JLabel lblMbRemove = new JLabel("MB - remove element");
		lblMbRemove.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblMbRemove.setBounds(0, 525, 153, 14);
		toolsPanel.add(lblMbRemove);

		JLabel lblRbSet = new JLabel("RB - set streets direction");
		lblRbSet.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblRbSet.setBounds(0, 536, 202, 14);
		toolsPanel.add(lblRbSet);

		lblMapsScale = new JLabel("Map's Scale");
		lblMapsScale.setBounds(10, 354, 86, 14);
		toolsPanel.add(lblMapsScale);

		textGarbage = new JTextField();
		textGarbage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					arg0.consume();
				}
			}
		});
		textGarbage.setBounds(81, 395, 41, 20);
		toolsPanel.add(textGarbage);
		textGarbage.setColumns(10);

		lblTruckCapacity = new JLabel("Truck's capacity:");
		lblTruckCapacity.setBounds(10, 379, 128, 14);
		toolsPanel.add(lblTruckCapacity);

		textScale = new JTextField();
		textScale.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					arg0.consume();
				}
			}
		});
		textScale.setBounds(81, 351, 41, 20);
		toolsPanel.add(textScale);
		textScale.setColumns(10);

		lblGarbage = new JLabel("Garbage");
		lblGarbage.setBounds(14, 398, 86, 14);
		toolsPanel.add(lblGarbage);

		lblGas = new JLabel("Gas");
		lblGas.setBounds(14, 423, 46, 14);
		toolsPanel.add(lblGas);

		textGas = new JTextField();
		textGas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					arg0.consume();
				}
			}
		});
		textGas.setColumns(10);
		textGas.setBounds(81, 420, 41, 20);
		toolsPanel.add(textGas);

		final JButton btnSaveCalculate = new JButton("Save & calculate");
		btnSaveCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(null);
				String path = null;

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					path = fc.getSelectedFile().getAbsolutePath();
				} else {

				}

				btnSaveCalculate.setEnabled(false);

				try{
					ProgramData.deleteInstance();

					ProgramData.getInstance().setMultiple(Integer.parseInt(textScale.getText()));
					ProgramData.getInstance().getTruck().setCapacity(Integer.parseInt(textGarbage.getText()));
					ProgramData.getInstance().getTruck().setFuel(Integer.parseInt(textGas.getText()));

					if(ProgramData.getInstance().getTruck().getCapacity() < 100)
						JOptionPane.showMessageDialog(null,"Minimum garbage capacity is 100!", "ERROR",JOptionPane.ERROR_MESSAGE);
					panel.startAlgorithm();

					System.out.println("Ready to go!");
					System.out.print("Saving...   ");
					if(path != null)
						ProgramData.serialize(path);

					System.out.println("Saved!");

					MainWindow window = new MainWindow();
					window.frmAAlgorithmWaste.setVisible(true);
					dispose();

				}
				catch(Exception e) {
					if(e.getMessage() != null) {
						if(e.getMessage().equals("fuelError"))
							JOptionPane.showMessageDialog(null,"It isn't enough gas!", "ERROR",JOptionPane.ERROR_MESSAGE);
						else if (e.getMessage().equals("1"))
							JOptionPane.showMessageDialog(null,"The map needs an initial node, a gas station and a dump!", "ERROR",JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(null,"You need to fill all fields!", "ERROR",JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null,"You need to fill all fields!", "ERROR",JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();
					System.out.println("ERROR");
					btnSaveCalculate.setEnabled(true);
				}
			}
		});
		btnSaveCalculate.setBounds(0, 485, 138, 23);
		toolsPanel.add(btnSaveCalculate);



		panel.requestFocus();

	}

	private class HandlerIcon implements ItemListener {
		private int modo;
		HandlerIcon(int modo) {
			this.modo = modo;
		}
		public void itemStateChanged(ItemEvent event) {
			panel.setIcon(modo);
		}

	}
}
