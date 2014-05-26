package gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;

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
		panel.setBounds(135, 11, 439, 550);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel);

		JPanel toolsPanel = new JPanel();
		toolsPanel.setBounds(10, 11, 122, 550);
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

		ButtonGroup options = new ButtonGroup();
		options.add(rdbtnGarbageBin);
		options.add(rdbtnStreet);
		options.add(rdbtnGarbageDeposit);
		options.add(rdbtnPetrolStation);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.startAlgorithm();
			}
		});
		btnCalcular.setBounds(10, 388, 89, 23);
		toolsPanel.add(btnCalcular);
		
		JLabel lblLbAdd = new JLabel("LB - add element");
		lblLbAdd.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblLbAdd.setBounds(0, 445, 102, 14);
		toolsPanel.add(lblLbAdd);
		
		JLabel lblMbRemove = new JLabel("MB - remove element");
		lblMbRemove.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblMbRemove.setBounds(0, 462, 153, 14);
		toolsPanel.add(lblMbRemove);
		
		JLabel lblRbSet = new JLabel("RB - set streets direction");
		lblRbSet.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblRbSet.setBounds(0, 480, 202, 14);
		toolsPanel.add(lblRbSet);

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
