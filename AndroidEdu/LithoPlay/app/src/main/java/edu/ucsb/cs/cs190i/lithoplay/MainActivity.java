package edu.ucsb.cs.cs190i.lithoplay;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final ComponentContext context = new ComponentContext(this);

    final RecyclerBinder recyclerBinder = new RecyclerBinder(
        context,
        new LinearLayoutInfo(this, OrientationHelper.VERTICAL, false));

    final Component component = Recycler.create(context)
        .binder(recyclerBinder)
        .build();

    addContent(recyclerBinder, context);
    setContentView(LithoView.create(context, component));
  }

  private static void addContent(
      RecyclerBinder recyclerBinder,
      ComponentContext context) {

    for (int i = 0; i < 32; i++) {
      ComponentInfo.Builder componentInfoBuilder = ComponentInfo.create();

      componentInfoBuilder.component(
          ListItem.create(context)
              .color(i % 2 == 0 ? Color.WHITE : Color.LTGRAY)
              .title("Hello, world!")
              .subtitle("Litho tutorial")
              .build());

      recyclerBinder.insertItemAt(i, componentInfoBuilder.build());
    }
  }
}
