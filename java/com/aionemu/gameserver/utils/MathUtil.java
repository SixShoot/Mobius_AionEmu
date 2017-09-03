/*
 * This file is part of the Aion-Emu project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.utils;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.aionemu.gameserver.controllers.movement.NpcMoveController;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.geometry.Point3D;
import com.aionemu.gameserver.model.templates.zone.Point2D;

public class MathUtil
{
	public static double getDistance(Point2D point1, Point2D point2)
	{
		return getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
	}
	
	public static double getDistance(float x1, float y1, float x2, float y2)
	{
		final float dx = x2 - x1;
		final float dy = y2 - y1;
		//
		// return ((Math.abs(dx) + Math.abs(dy))/2);
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public static double getDistance(Point3D point1, Point3D point2)
	{
		if ((point1 == null) || (point2 == null))
		{
			return 0;
		}
		return getDistance(point1.getX(), point1.getY(), point1.getZ(), point2.getX(), point2.getY(), point2.getZ());
	}
	
	public static double getDistance(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		final float dx = x1 - x2;
		final float dy = y1 - y2;
		final float dz = z1 - z2;
		return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
	}
	
	public static double getDistance(VisibleObject object, float x, float y, float z)
	{
		return getDistance(object.getX(), object.getY(), object.getZ(), x, y, z);
	}
	
	public static double getDistance(VisibleObject object, VisibleObject object2)
	{
		return getDistance(object.getX(), object.getY(), object.getZ(), object2.getX(), object2.getY(), object2.getZ());
	}
	
	public static Point2D getClosestPointOnSegment(Point ss, Point se, Point p)
	{
		return getClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
	}
	
	public static Point2D getClosestPointOnSegment(float sx1, float sy1, float sx2, float sy2, float px, float py)
	{
		final double xDelta = sx2 - sx1;
		final double yDelta = sy2 - sy1;
		if ((xDelta == 0) && (yDelta == 0))
		{
			throw new IllegalArgumentException("Segment start equals segment end");
		}
		final double u = (((px - sx1) * xDelta) + ((py - sy1) * yDelta)) / ((xDelta * xDelta) + (yDelta * yDelta));
		final Point2D closestPoint;
		if (u < 0)
		{
			closestPoint = new Point2D(sx1, sy1);
		}
		else if (u > 1)
		{
			closestPoint = new Point2D(sx2, sy2);
		}
		else
		{
			closestPoint = new Point2D((float) (sx1 + (u * xDelta)), (float) (sy1 + (u * yDelta)));
		}
		return closestPoint;
	}
	
	public static double getDistanceToSegment(Point ss, Point se, Point p)
	{
		return getDistanceToSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
	}
	
	public static double getDistanceToSegment(int sx1, int sy1, int sx2, int sy2, int px, int py)
	{
		final Point2D closestPoint = getClosestPointOnSegment(sx1, sy1, sx2, sy2, px, py);
		return getDistance(closestPoint.getX(), closestPoint.getY(), px, py);
	}
	
	public static boolean isInRange(VisibleObject object1, VisibleObject object2, float range)
	{
		if ((object1.getWorldId() != object2.getWorldId()) || (object1.getInstanceId() != object2.getInstanceId()))
		{
			return false;
		}
		final float dx = (object2.getX() - object1.getX());
		final float dy = (object2.getY() - object1.getY());
		return ((dx * dx) + (dy * dy)) < (range * range);
	}
	
	public static boolean isIn3dRange(VisibleObject object1, VisibleObject object2, float range)
	{
		if ((object1.getWorldId() != object2.getWorldId()) || (object1.getInstanceId() != object2.getInstanceId()))
		{
			return false;
		}
		final float dx = (object2.getX() - object1.getX());
		final float dy = (object2.getY() - object1.getY());
		final float dz = (object2.getZ() - object1.getZ());
		return ((dx * dx) + (dy * dy) + (dz * dz)) < (range * range);
	}
	
	public static boolean isIn3dRangeLimited(VisibleObject object1, VisibleObject object2, float minRange, float maxRange)
	{
		if ((object1.getWorldId() != object2.getWorldId()) || (object1.getInstanceId() != object2.getInstanceId()))
		{
			return false;
		}
		final float dx = (object2.getX() - object1.getX());
		final float dy = (object2.getY() - object1.getY());
		final float dz = (object2.getZ() - object1.getZ());
		return (((dx * dx) + (dy * dy) + (dz * dz)) > (minRange * minRange)) && (((dx * dx) + (dy * dy) + (dz * dz)) < (maxRange * maxRange));
	}
	
	public static boolean isIn3dRange(final float obj1X, final float obj1Y, final float obj1Z, final float obj2X, final float obj2Y, final float obj2Z, float range)
	{
		final float dx = (obj2X - obj1X);
		final float dy = (obj2Y - obj1Y);
		final float dz = (obj2Z - obj1Z);
		return ((dx * dx) + (dy * dy) + (dz * dz)) < (range * range);
	}
	
	public static boolean isInSphere(final VisibleObject obj, final float centerX, final float centerY, final float centerZ, final float radius)
	{
		final float dx = (obj.getX() - centerX);
		final float dy = (obj.getY() - centerY);
		final float dz = (obj.getZ() - centerZ);
		return ((dx * dx) + (dy * dy) + (dz * dz)) < (radius * radius);
	}
	
	public static float calculateAngleFrom(float obj1X, float obj1Y, float obj2X, float obj2Y)
	{
		float angleTarget = (float) Math.toDegrees(Math.atan2(obj2Y - obj1Y, obj2X - obj1X));
		if (angleTarget < 0)
		{
			angleTarget = 360 + angleTarget;
		}
		return angleTarget;
	}
	
	public static float calculateAngleFrom(VisibleObject obj1, VisibleObject obj2)
	{
		return calculateAngleFrom(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
	}
	
	public static float convertHeadingToDegree(byte clientHeading)
	{
		final float degree = clientHeading * 3;
		return degree;
	}
	
	public static byte convertDegreeToHeading(float angle)
	{
		return (byte) (angle / 3);
	}
	
	public static boolean isNearCoordinates(VisibleObject obj, float x, float y, float z, int offset)
	{
		return getDistance(obj.getX(), obj.getY(), obj.getZ(), x, y, z) < (offset + NpcMoveController.MOVE_CHECK_OFFSET);
	}
	
	public static boolean isNearCoordinates(VisibleObject obj, VisibleObject obj2, int offset)
	{
		return getDistance(obj.getX(), obj.getY(), obj.getZ(), obj2.getX(), obj2.getY(), obj2.getZ()) < (offset + NpcMoveController.MOVE_CHECK_OFFSET);
	}
	
	public static boolean isInAttackRange(Creature object1, Creature object2, float range)
	{
		if ((object1 == null) || (object2 == null))
		{
			return false;
		}
		if ((object1.getWorldId() != object2.getWorldId()) || (object1.getInstanceId() != object2.getInstanceId()))
		{
			return false;
		}
		float offset = object1.getObjectTemplate().getBoundRadius().getCollision() + object2.getObjectTemplate().getBoundRadius().getCollision();
		if (object1.getMoveController().isInMove())
		{
			offset = +3f;
		}
		if (object2.getMoveController().isInMove())
		{
			offset = +3f;
		}
		return ((getDistance(object1, object2) - offset) <= range);
	}
	
	public static boolean isInsideAttackCylinder(VisibleObject obj1, VisibleObject obj2, int length, int radius, boolean isFront)
	{
		final double radian = Math.toRadians(convertHeadingToDegree(obj1.getHeading()));
		final int direction = isFront ? 0 : 1;
		final float dx = (float) (Math.cos((Math.PI * direction) + radian) * length);
		final float dy = (float) (Math.sin((Math.PI * direction) + radian) * length);
		final float tdx = obj2.getX() - obj1.getX();
		final float tdy = obj2.getY() - obj1.getY();
		final float tdz = obj2.getZ() - obj1.getZ();
		final float lengthSqr = length * length;
		final float dot = (tdx * dx) + (tdy * dy);
		if ((dot < 0.0f) || (dot > lengthSqr))
		{
			return false;
		}
		return (((tdx * tdx) + (tdy * tdy) + (tdz * tdz)) - ((dot * dot) / lengthSqr)) <= radius;
	}
	
	public static Point get2DPointInsideCircle(float CenterX, float CenterY, int Radius)
	{
		final double X = (Math.random() * 2) - 1;
		final double YMin = -Math.sqrt(1 - (X * X));
		final double YMax = Math.sqrt(1 - (X * X));
		final double Y = (Math.random() * (YMax - YMin)) + YMin;
		final double finalX = (X * Radius) + CenterX;
		final double finalY = (Y * Radius) + CenterY;
		return new Point((int) finalX, (int) finalY);
	}
	
	public static Point get2DPointOnCircleCircumference(float CenterX, float CenterY, int Radius, float angleInDegrees)
	{
		final float finalX = (float) (Radius * Math.cos((angleInDegrees * Math.PI) / 180F)) + CenterX;
		final float finalY = (float) (Radius * Math.sin((angleInDegrees * Math.PI) / 180F)) + CenterY;
		return new Point((int) finalX, (int) finalY);
	}
	
	public static Point get2DPointOnCircleCircumference(Point CenterPoint, Point EndPoint, int Radius)
	{
		final double AngleinXAxis = getAngle(CenterPoint, EndPoint);
		final float finalX = (float) (Radius * Math.cos((AngleinXAxis * Math.PI) / 180F)) + CenterPoint.x;
		final float finalY = (float) (Radius * Math.sin((AngleinXAxis * Math.PI) / 180F)) + CenterPoint.y;
		return new Point((int) finalX, (int) finalY);
	}
	
	public static double getAngle(Point P1, Point P2)
	{
		final float dx = P2.x - P1.x;
		final float dy = P2.y - P1.y;
		final double angle = (Math.atan2(dx, dy) * 180) / Math.PI;
		return angle;
	}
	
	public static Point get2DPointInsideCircleClosestTo(Point Center, int Radius, Point GivenPoint)
	{
		final double vX = GivenPoint.x - Center.x;
		final double vY = GivenPoint.y - Center.y;
		final double magV = Math.sqrt((vX * vX) + (vY * vY));
		final double aX = Center.x + ((vX / magV) * Radius);
		final double aY = Center.y + ((vY / magV) * Radius);
		return new Point((int) aX, (int) aY);
	}
	
	public static Point get2DPointInsideAnnulus(Point Center, int Radius1, int Radius2)
	{
		final double theta = 360 * Math.random();
		final double dist = Math.sqrt((Math.random() * ((Radius1 * Radius1) - (Radius2 * Radius2))) + (Radius2 * Radius2));
		final double X = (dist * Math.cos(theta)) + Center.x;
		final double Y = (dist * Math.sin(theta)) + Center.y;
		return new Point((int) X, (int) Y);
	}
	
	public static boolean isInAnnulus(final VisibleObject obj, Point3D Center, float Radius1, float Radius2)
	{
		if (!isInSphere(obj, Center.getX(), Center.getY(), Center.getZ(), Radius2))
		{
			if (isInSphere(obj, Center.getX(), Center.getY(), Center.getZ(), Radius1))
			{
				return true;
			}
		}
		return false;
	}
	
	static final BigDecimal TWO = new BigDecimal(2);
	static final double SQRT_10 = 3.162277660168379332;
	
	public static BigDecimal bigSqrt(BigDecimal squarD, MathContext rootMC)
	{
		final int sign = squarD.signum();
		if (sign == -1)
		{
			throw new ArithmeticException("\nSquare root of a negative number: " + squarD);
		}
		else if (sign == 0)
		{
			return squarD.round(rootMC);
		}
		final int prec = rootMC.getPrecision();
		if (prec == 0)
		{
			throw new IllegalArgumentException("\nMost roots won't have infinite precision = 0");
		}
		final int BITS = 62;
		final int nInit = 16;
		MathContext nMC = new MathContext(18, RoundingMode.HALF_DOWN);
		BigDecimal x = null, e = null;
		BigDecimal v = null, g = null;
		BigInteger bi = squarD.unscaledValue();
		final int biLen = bi.bitLength();
		final int shift = Math.max(0, (biLen - BITS) + ((biLen % 2) == 0 ? 0 : 1));
		bi = bi.shiftRight(shift);
		double root = Math.sqrt(bi.doubleValue());
		final BigDecimal halfBack = new BigDecimal(BigInteger.ONE.shiftLeft(shift / 2));
		int scale = squarD.scale();
		if ((scale % 2) == 1)
		{
			root *= SQRT_10;
		}
		scale = (int) Math.floor(scale / 2.);
		x = new BigDecimal(root, nMC);
		x = x.multiply(halfBack, nMC);
		if (scale != 0)
		{
			x = x.movePointLeft(scale);
		}
		if (prec < nInit)
		{
			return x.round(rootMC);
		}
		v = BigDecimal.ONE.divide(TWO.multiply(x), nMC);
		final ArrayList<Integer> nPrecs = new ArrayList<>();
		assert nInit > 3 : "Never ending loop!";
		for (int m = prec + 1; m > nInit; m = (m / 2) + (m > 100 ? 1 : 2))
		{
			nPrecs.add(m);
		}
		for (int i = nPrecs.size() - 1; i > -1; i--)
		{
			nMC = new MathContext(nPrecs.get(i), ((i % 2) == 1) ? RoundingMode.HALF_UP : RoundingMode.HALF_DOWN);
			e = squarD.subtract(x.multiply(x, nMC), nMC);
			if (i != 0)
			{
				x = x.add(e.multiply(v, nMC));
			}
			else
			{
				x = x.add(e.multiply(v, rootMC), rootMC);
				break;
			}
			g = BigDecimal.ONE.subtract(TWO.multiply(x).multiply(v, nMC));
			v = v.add(g.multiply(v, nMC));
		}
		return x;
	}
}