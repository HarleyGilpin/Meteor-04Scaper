public class Draw2D extends Hashable {

	public static int[] data;

	public static int width2d;

	public static int height2d;

	public static int top;

	public static int bottom;

	public static int left;

	public static int right;

	public static int boundX;

	public static int centerX2d;

	public static int centerY2d;

	protected Draw2D() {
	}

	public static void bind( int width, int height, int[] src) {
		data = src;
		width2d = width;
		height2d = height;
		setBounds(height, width, 0, 0);
	}

	public static void resetBounds() {
		left = 0;
		top = 0;
		right = width2d;
		bottom = height2d;
		boundX = right - 1;
		centerX2d = right / 2;
	}

	public static void setBounds( int bottom, int right, int top, int left) {
		if (left < 0) {
			left = 0;
		}

		if (top < 0) {
			top = 0;
		}

		if (right > width2d) {
			right = width2d;
		}

		if (bottom > height2d) {
			bottom = height2d;
		}

		Draw2D.left = left;
		Draw2D.top = top;
		Draw2D.right = right;
		Draw2D.bottom = bottom;
		boundX = Draw2D.right - 1;
		centerX2d = Draw2D.right / 2;
		centerY2d = Draw2D.bottom / 2;
	}

	public static void clear() {
		int len = width2d * height2d;
		for ( int i = 0; i < len; i++) {
			data[i] = 0;
		}
	}

	public static void fillRect( int x, int y, int rgb, int width, int height) {
		if (x < left) {
			width -= left - x;
			x = left;
		}

		if (y < top) {
			height -= top - y;
			y = top;
		}

		if (x + width > right) {
			width = right - x;
		}

		if (y + height > bottom) {
			height = bottom - y;
		}

		int step = width2d - width;
		int offset = x + y * width2d;
		for ( int i = -height; i < 0; i++) {
			for ( int j = -width; j < 0; j++) {
				data[offset++] = rgb;
			}

			offset += step;
		}
	}

	public static void fillRectAlpha( int x, int y, int width, int height, int rgb, int alpha) {
		if (x < left) {
			width -= left - x;
			x = left;
		}
		if (y < top) {
			height -= top - y;
			y = top;
		}
		if (x + width > right) {
			width = right - x;
		}
		if (y + height > bottom) {
			height = bottom - y;
		}
		int invAlpha = 256 - alpha;
		int r0 = (rgb >> 16 & 0xFF) * alpha;
		int g0 = (rgb >> 8 & 0xFF) * alpha;
		int b0 = (rgb & 0xFF) * alpha;
		int step = width2d - width;
		int offset = x + y * width2d;
		for ( int i = 0; i < height; i++) {
			for ( int j = -width; j < 0; j++) {
				int r1 = (data[offset] >> 16 & 0xFF) * invAlpha;
				int g1 = (data[offset] >> 8 & 0xFF) * invAlpha;
				int b1 = (data[offset] & 0xFF) * invAlpha;
				int color = (r0 + r1 >> 8 << 16) + (g0 + g1 >> 8 << 8) + (b0 + b1 >> 8);
				data[offset++] = color;
			}
			offset += step;
		}
	}

	public static void fillCircle( int xCenter, int yCenter, int yRadius, int rgb, int alpha) {
		int invAlpha = 256 - alpha;
		int r0 = (rgb >> 16 & 0xFF) * alpha;
		int g0 = (rgb >> 8 & 0xFF) * alpha;
		int b0 = (rgb & 0xFF) * alpha;
		int yStart = yCenter - yRadius;
		if (yStart < 0) {
			yStart = 0;
		}
		int yEnd = yCenter + yRadius;
		if (yEnd >= height2d) {
			yEnd = height2d - 1;
		}
		for ( int y = yStart; y <= yEnd; y++) {
			int midpoint = y - yCenter;
			int xRadius = (int) Math.sqrt((double) (yRadius * yRadius - midpoint * midpoint));
			int xStart = xCenter - xRadius;
			if (xStart < 0) {
				xStart = 0;
			}
			int xEnd = xCenter + xRadius;
			if (xEnd >= width2d) {
				xEnd = width2d - 1;
			}
			int offset = xStart + y * width2d;
			for ( int x = xStart; x <= xEnd; x++) {
				int r1 = (data[offset] >> 16 & 0xFF) * invAlpha;
				int g1 = (data[offset] >> 8 & 0xFF) * invAlpha;
				int b1 = (data[offset] & 0xFF) * invAlpha;
				int color = (r0 + r1 >> 8 << 16) + (g0 + g1 >> 8 << 8) + (b0 + b1 >> 8);
				data[offset++] = color;
			}
		}
	}

	public static void drawRect( int x, int y, int rgb, int width, int height) {
		drawHorizontalLine(x, y, rgb, width);
		drawHorizontalLine(x, y + height - 1, rgb, width);
		drawVerticalLine(x, y, rgb, height);
		drawVerticalLine(x + width - 1, y, rgb, height);
	}

	public static void drawHorizontalLine( int x, int y, int rgb, int width) {
		if (y < top || y >= bottom) {
			return;
		}

		if (x < left) {
			width -= left - x;
			x = left;
		}

		if (x + width > right) {
			width = right - x;
		}

		int off = x + y * width2d;
		for ( int i = 0; i < width; i++) {
			data[off + i] = rgb;
		}
	}

	public static void drawVerticalLine( int x, int y, int rgb, int height) {
		if (x < left || x >= right) {
			return;
		}

		if (y < top) {
			height -= top - y;
			y = top;
		}

		if (y + height > bottom) {
			height = bottom - y;
		}

		int off = x + y * width2d;
		for ( int i = 0; i < height; i++) {
			data[off + i * width2d] = rgb;
		}
	}

	public static void drawLine(int x1, int y1, int x2, int y2, int rgb) {
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;

		int err = dx - dy;

		while (true) {
			if ((x1 >= left) && (x1 < right) && (y1 >= top) && (y1 < bottom)) {
				data[x1 + (y1 * width2d)] = rgb;
			}

			if ((x1 == x2) && (y1 == y2)) {
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy) {
				err = err - dy;
				x1 = x1 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y1 = y1 + sy;
			}
		}
	}
}
