Name: Xuan Wang
Email: xuanwang@umail.ucsb.edu
Perm# 9611435

1. I used a customized SurfaceView implementing SurfaceHolder.Callback to response to touch events.
2. In the onTouchEvent method, it handles touching events, and draws circles for all touching events,
   and draws lines connecting circles in a MotionEvent.ACTION_MOVE event.
3. The customized surfaceview will draw white color to the canvas and the bitmap in its clear() method.
   This method will be called in the onClickListener of the clear button of the activity.
