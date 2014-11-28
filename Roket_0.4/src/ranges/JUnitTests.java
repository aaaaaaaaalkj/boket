package ranges;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JUnitTests {

	@Test
	public void testUngroup() {
		SimpleRange r;
		r = GroupedRange._22.ungroup();

		assertTrue(r.contains(ElementRange._2c_2d));
		assertTrue(r.contains(ElementRange._2c_2h));
		assertTrue(r.contains(ElementRange._2c_2s));
		assertTrue(r.contains(ElementRange._2h_2d));
		assertTrue(r.contains(ElementRange._2s_2h));
		assertTrue(r.contains(ElementRange._2s_2d));
		assertTrue(r.size() == 6);

		r = GroupedRange._32o.ungroup();

		assertTrue(r.contains(ElementRange._3c_2d));
		assertTrue(r.contains(ElementRange._3c_2h));
		assertTrue(r.contains(ElementRange._3c_2s));
		assertTrue(r.contains(ElementRange._3h_2d));
		assertTrue(r.contains(ElementRange._3s_2h));
		assertTrue(r.contains(ElementRange._3s_2d));

		assertTrue(r.contains(ElementRange._3d_2c));
		assertTrue(r.contains(ElementRange._3h_2c));
		assertTrue(r.contains(ElementRange._3s_2c));
		assertTrue(r.contains(ElementRange._3d_2h));
		assertTrue(r.contains(ElementRange._3h_2s));
		assertTrue(r.contains(ElementRange._3d_2s));
		assertTrue(r.size() == 12);

		r = GroupedRange._32.ungroup();

		assertTrue(r.contains(ElementRange._3c_2c));
		assertTrue(r.contains(ElementRange._3h_2h));
		assertTrue(r.contains(ElementRange._3s_2s));
		assertTrue(r.contains(ElementRange._3d_2d));
		assertTrue(r.size() == 4);
	}

	@Test
	public void testUngroupPlus() {
		GroupedPlusRange range;
		SimpleRange res;

		range = new GroupedPlusRange(GroupedRange._52o);
		res = range.ungroup();

		assertTrue(res.contains(ElementRange._5s_2h));
		assertTrue(res.contains(ElementRange._5s_3h));
		assertTrue(res.contains(ElementRange._5s_4h));
		assertTrue(res.contains(ElementRange._5c_2h));
		assertTrue(res.contains(ElementRange._5c_3h));
		assertTrue(res.contains(ElementRange._5c_4h));
		assertTrue(res.contains(ElementRange._5d_2h));
		assertTrue(res.contains(ElementRange._5d_3h));
		assertTrue(res.contains(ElementRange._5d_4h));

		assertTrue(res.contains(ElementRange._5s_2d));
		assertTrue(res.contains(ElementRange._5s_3d));
		assertTrue(res.contains(ElementRange._5s_4d));
		assertTrue(res.contains(ElementRange._5c_2d));
		assertTrue(res.contains(ElementRange._5c_3d));
		assertTrue(res.contains(ElementRange._5c_4d));
		assertTrue(res.contains(ElementRange._5h_2d));
		assertTrue(res.contains(ElementRange._5h_3d));
		assertTrue(res.contains(ElementRange._5h_4d));

		assertTrue(res.contains(ElementRange._5h_2s));
		assertTrue(res.contains(ElementRange._5h_3s));
		assertTrue(res.contains(ElementRange._5h_4s));
		assertTrue(res.contains(ElementRange._5c_2s));
		assertTrue(res.contains(ElementRange._5c_3s));
		assertTrue(res.contains(ElementRange._5c_4s));
		assertTrue(res.contains(ElementRange._5d_2s));
		assertTrue(res.contains(ElementRange._5d_3s));
		assertTrue(res.contains(ElementRange._5d_4s));

		assertTrue(res.contains(ElementRange._5h_2c));
		assertTrue(res.contains(ElementRange._5h_3c));
		assertTrue(res.contains(ElementRange._5h_4c));
		assertTrue(res.contains(ElementRange._5s_2c));
		assertTrue(res.contains(ElementRange._5s_3c));
		assertTrue(res.contains(ElementRange._5s_4c));
		assertTrue(res.contains(ElementRange._5d_2c));
		assertTrue(res.contains(ElementRange._5d_3c));
		assertTrue(res.contains(ElementRange._5d_4c));

		assertFalse(res.contains(ElementRange._5d_4d));
		assertFalse(res.contains(ElementRange._5d_2d));
		assertFalse(res.contains(ElementRange._5h_5d));

		assertFalse(res.contains(ElementRange._6d_3h));
		assertFalse(res.contains(ElementRange._6d_2d));
		assertFalse(res.contains(ElementRange._6h_3d));

		range = new GroupedPlusRange(GroupedRange._42);
		res = range.ungroup();

		assertTrue(res.contains(ElementRange._4s_2s));
		assertTrue(res.contains(ElementRange._4h_3h));

		assertFalse(res.contains(ElementRange._4s_3h));
		assertFalse(res.contains(ElementRange._4s_2h));
		assertFalse(res.contains(ElementRange._4s_4h));
		assertFalse(res.contains(ElementRange._3s_3h));
		assertFalse(res.contains(ElementRange._5s_3h));
		assertFalse(res.contains(ElementRange._5h_3h));

		range = new GroupedPlusRange(GroupedRange.QQ);
		res = range.ungroup();

		assertTrue(res.contains(ElementRange.Qc_Qd));
		assertTrue(res.contains(ElementRange.Qs_Qd));
		assertTrue(res.contains(ElementRange.Qh_Qd));
		assertTrue(res.contains(ElementRange.Qc_Qs));
		assertTrue(res.contains(ElementRange.Qs_Qh));
		assertTrue(res.contains(ElementRange.Qc_Qh));

		assertTrue(res.contains(ElementRange.Kc_Kd));
		assertTrue(res.contains(ElementRange.Ks_Kd));
		assertTrue(res.contains(ElementRange.Kh_Kd));
		assertTrue(res.contains(ElementRange.Kc_Ks));
		assertTrue(res.contains(ElementRange.Ks_Kh));
		assertTrue(res.contains(ElementRange.Kc_Kh));

		assertTrue(res.contains(ElementRange.Ac_Ad));
		assertTrue(res.contains(ElementRange.As_Ad));
		assertTrue(res.contains(ElementRange.Ah_Ad));
		assertTrue(res.contains(ElementRange.Ac_As));
		assertTrue(res.contains(ElementRange.As_Ah));
		assertTrue(res.contains(ElementRange.Ac_Ah));

		assertFalse(res.contains(ElementRange.Ac_Qh));
		assertFalse(res.contains(ElementRange.Ac_Kh));
		assertFalse(res.contains(ElementRange.Ac_Kc));
		assertFalse(res.contains(ElementRange.Jc_Jh));
	}

	@Test
	public void testGrouped() {
		assertTrue(ElementRange._3h_2s.grouped() == GroupedRange._32o);
		assertTrue(ElementRange._3h_2h.grouped() == GroupedRange._32);
		assertTrue(ElementRange._3s_3h.grouped() == GroupedRange._33);
	}

	@Test
	public void testGrouptedPlus() {
		GroupedPlusRange r;

		r = new GroupedPlusRange(GroupedRange._22);
		assertTrue(r.size() == 78);
		assertTrue(r.contains(ElementRange.Ac_Ad));
		assertTrue(r.contains(ElementRange._2c_2h));
		assertTrue(r.contains(ElementRange.Tc_Td));

		assertFalse(r.contains(ElementRange.Ac_Kd));
		assertFalse(r.contains(ElementRange.Ac_Kc));

		r = new GroupedPlusRange(GroupedRange.A4o);
		assertTrue(r.size() == 120);
		assertTrue(r.contains(ElementRange.Ac_Qd));
		assertTrue(r.contains(ElementRange.Ad_4h));
		assertTrue(r.contains(ElementRange.As_8d));

		assertFalse(r.contains(ElementRange.Ac_Ad));
		assertFalse(r.contains(ElementRange.Ks_8d));

		r = new GroupedPlusRange(GroupedRange._32);
		assertTrue(r.size() == 4);
		assertTrue(r.contains(ElementRange._3c_2c));
		assertTrue(r.contains(ElementRange._3h_2h));
		assertTrue(r.contains(ElementRange._3s_2s));
		assertTrue(r.contains(ElementRange._3d_2d));

		assertFalse(r.contains(ElementRange._3d_2c));
		assertFalse(r.contains(ElementRange._3c_3d));
		assertFalse(r.contains(ElementRange._4c_3d));
		assertFalse(r.contains(ElementRange._2c_2d));

	}
}
