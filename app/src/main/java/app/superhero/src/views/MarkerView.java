package app.superhero.src.views;


import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import app.superhero.R;

public class MarkerView extends com.github.mikephil.charting.components.MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    public MarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        if (highlight != null) {
            ((TextView) this.findViewById(R.id.markerText)).setText(String.valueOf((int) highlight.getY()));
            ((TextView) this.findViewById(R.id.markerText)).setText(String.valueOf((int) highlight.getY()));
        }
    }
}
