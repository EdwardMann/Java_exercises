import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class KeyMetricsPanel extends JPanel {

	JPanel metrics;

	JLabel impressionsval;
	JLabel clicksval;
	JLabel uniquesval;
	JLabel bouncesTimeval;
	JLabel bouncesPageval;
	JLabel conversionsval;
	JLabel costval;
	JLabel ctrval;
	JLabel cpaval;
	JLabel cpcval;
	JLabel cpmval;
	JLabel bounceTimeRateval;
	JLabel bounceRateval;

	KeyMetricsPanel() {
		init();
		initMetrics();
	}

	void init() {
		new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	void initMetrics() {
		metrics = new JPanel(new GridLayout(13, 2));
		metrics.setPreferredSize(new Dimension(340, 600));
		metrics.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Metrics", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		metrics.setSize(new Dimension(340, 600));
		add(metrics);
		/* Insert all the current key metrics */
		JLabel impressions = new JLabel("Impressions:");
		JLabel clicks = new JLabel("Clicks:");
		JLabel uniques = new JLabel("Uniques:");
		JLabel bouncesTime = new JLabel("Bounces (Time):");
		JLabel bouncesPages = new JLabel("Bounces (Pages):");
		JLabel conversions = new JLabel("Conversions:");
		JLabel cost = new JLabel("Total Cost:");
		JLabel ctr = new JLabel("CTR:");
		ctr.setToolTipText("Click-through-rate - The average number of clicks per impression.");
		JLabel cpa = new JLabel("CPA:");
		cpa.setToolTipText("Cost-per-acquisition - The average amount of money spent on an "
				+ "advertising campaign for each conversion.");
		JLabel cpc = new JLabel("CPC:");
		cpc.setToolTipText(
				"Cost-per-click - The average amount of money spent on an " + "advertising campaign for each click.");
		JLabel cpm = new JLabel("CPM:");
		cpm.setToolTipText("Cost-per-thousand impressions - The average amount of money spent "
				+ "on an advertising campaign for every one thousand impressions.");
		JLabel bounceTimeRate = new JLabel("Bounce Rate By Time:");
		JLabel bounceRate = new JLabel("Bounce Rate By Pages:");

		impressionsval = new JLabel("", JLabel.CENTER);
		clicksval = new JLabel("", JLabel.CENTER);
		uniquesval = new JLabel("", JLabel.CENTER);
		bouncesTimeval = new JLabel("", JLabel.CENTER);
		bouncesPageval = new JLabel("", JLabel.CENTER);
		conversionsval = new JLabel("", JLabel.CENTER);
		costval = new JLabel("", JLabel.CENTER);
		ctrval = new JLabel("", JLabel.CENTER);
		cpaval = new JLabel("", JLabel.CENTER);
		cpcval = new JLabel("", JLabel.CENTER);
		cpmval = new JLabel("", JLabel.CENTER);
		bounceTimeRateval = new JLabel("", JLabel.CENTER);
		bounceRateval = new JLabel("", JLabel.CENTER);

		metrics.add(impressions);		metrics.add(impressionsval);
		metrics.add(clicks);			metrics.add(clicksval);
		metrics.add(uniques);			metrics.add(uniquesval);
		metrics.add(bouncesTime);		metrics.add(bouncesTimeval);
		metrics.add(bouncesPages);		metrics.add(bouncesPageval);
		metrics.add(conversions);		metrics.add(conversionsval);
		metrics.add(cost);				metrics.add(costval);
		metrics.add(cpc);				metrics.add(cpcval);
		metrics.add(ctr);				metrics.add(ctrval);
		metrics.add(cpa);				metrics.add(cpaval);
		metrics.add(cpm);				metrics.add(cpmval);
		metrics.add(bounceTimeRate);	metrics.add(bounceTimeRateval);
		metrics.add(bounceRate);		metrics.add(bounceRateval);

	}

	JLabel getImpressions() {
		return impressionsval;
	}

	JLabel getClicks() {
		return clicksval;
	}

	JLabel getUniques() {
		return uniquesval;
	}

	JLabel getBouncesPages() {
		return bouncesPageval;
	}

	JLabel getBouncesTime() {
		return bouncesTimeval;
	}

	JLabel getConversions() {
		return conversionsval;
	}

	JLabel getCosts() {
		return costval;
	}

	JLabel getCTR() {
		return ctrval;
	}

	JLabel getCPA() {
		return cpaval;
	}

	JLabel getCPC() {
		return cpcval;
	}

	JLabel getCPM() {
		return cpmval;
	}

	JLabel getBounceRateTime() {
		return bounceTimeRateval;
	}
	
	JLabel getBounceRate() {
		return bounceRateval;
	}
}
