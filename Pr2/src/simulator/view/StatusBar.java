package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 7L;
	
	private Controller _ctrl;
	private JLabel _timeLabel;
	private JLabel _msgLabel;
	
	StatusBar(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(1)); 	// 1 hundido; 0 sobresale
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(MyColors.GRIS_CLARO);
		
		_timeLabel = new JLabel("Time:   0");
		_timeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		_timeLabel.setForeground(MyColors.BLANCO);
		_msgLabel = new JLabel("No changes");
		_msgLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		_msgLabel.setForeground(MyColors.BLANCO);
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setBackground(MyColors.BLANCO);
		
		toolBar.addSeparator(new Dimension(4, 20));
		toolBar.add(_timeLabel);
		toolBar.addSeparator(new Dimension(80, 20));
		toolBar.add(sep);
		toolBar.addSeparator(new Dimension(6, 20));
		toolBar.add(_msgLabel);
		
		
		this.add(toolBar);
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_timeLabel.setText("Time:   " + time);
		_msgLabel.setText("Advance to time " + time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_timeLabel.setText("Time:   " + time);
		_msgLabel.setText("Event added (" + e.toString() + ")");
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_timeLabel.setText("Time:   " + time);
		_msgLabel.setText("Reset done");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_timeLabel.setText("Time:   " + time);
	}

}