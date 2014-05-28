package kazutnb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FuncJava {

	private static void chkSize(List<?> list) {
		if (list.size() == 0) {
			throw new IllegalArgumentException();
		}
	}

	/*
	 * P01 (*) Find the last element of a list.
	 */
	static int last(List<Integer> list) {
		if (list.size() == 0) {
			throw new IllegalArgumentException();
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return last(list.subList(1, list.size()));
		}
	}

	/*
	 * P02 (*) Find the last but one element of a list.
	 */
	static int last2(List<Integer> list) {
		if (list.size() < 2) {
			throw new IllegalArgumentException();
		} else if (list.size() == 2) {
			return list.get(0);
		} else {
			return last2(list.subList(1, list.size()));
		}
	}

	/*
	 * P03 (*) Find the Kth element of a list.
	 */
	static int nth(int n, List<Integer> list) {
		if (list.size() == 0) {
			throw new IllegalArgumentException();
		}
		if (n == 0) {
			return list.get(0);
		} else {
			return nth(n - 1, list.subList(1, list.size()));
		}
	}

	/*
	 * P04 (*) Find the number of elements of a list.
	 */
	static int length(List<Integer> list) {
		return lengthRecursive(list, 0);
	}

	private static int lengthRecursive(List<Integer> list, int n) {
		if (list.size() == 0) {
			return n;
		} else {
			return lengthRecursive(list.subList(1, list.size()), n + 1);
		}
	}

	/*
	 * P05 (*) Reverse a list.
	 */
	static List<Integer> reverse(List<Integer> list) {
		if (list.size() == 0) {
			throw new IllegalArgumentException();
		} else {
			return reverseRecursive(list, new ArrayList<Integer>());
		}
	}

	private static List<Integer> reverseRecursive(List<Integer> list, List<Integer> reverseList) {
		if (list.size() == 0) {
			return reverseList;
		} else {
			reverseList.add(last(list));
			return reverseRecursive(list.subList(0, list.size() - 1), reverseList);
		}
	}

	/*
	 * P06 (*) Find out whether a list is a palindrome.
	 */
	static boolean isPalindrome(List<Integer> list) {
		// return list.equals(reverse(list));
		if (list.size() == 0) {
			throw new IllegalArgumentException();
		}
		return isPalindromeRecursive(list);
	}

	private static boolean isPalindromeRecursive(List<Integer> list) {
		if (list.size() == 0 || list.size() == 1) {
			return true;
		} else {
			if (list.get(0).equals(last(list))) {
				return isPalindromeRecursive(list.subList(1, list.size() - 1));
			} else {
				return false;
			}
		}
	}

	/*
	 * P07 (**) Flatten a nested list structure.
	 */
	static List flatten(List list) {
		return toFlatList(list, new ArrayList());
	}

	private static List toFlatList(List list, List flatList) {
		if (list.size() == 0)
			return flatList;
		if (list.get(0) instanceof List) {
			flatList.addAll(flatten((List) list.get(0)));
		} else {
			flatList.add(list.get(0));
		}
		return toFlatList(list.subList(1, list.size()), flatList);
	}

	/*
	 * P08 (**) Eliminate consecutive duplicates of list elements.
	 */
	static List compress(List list) {
		return compressBy(list, null, new ArrayList());
	}

	private static List compressBy(List list, Object key, List compressList) {
		if (list.size() == 0)
			return compressList;
		if (!list.get(0).equals(key)) {
			compressList.add(list.get(0));
		}
		return compressBy(list.subList(1, list.size()), list.get(0), compressList);
	}

	/*
	 * P09 (**) Pack consecutive duplicates of list elements into sublists.
	 */
	static List pack(List list) {
		if (list.size() <= 1) {
			return list;
		} else {
			return packBy(list, list.get(0), new ArrayList(), new ArrayList());
		}
	}

	private static List packBy(List list, Object key, List sameKeyList, List packList) {
		if (list.size() == 1)
			return packList;
		sameKeyList.add(list.get(0));
		if (list.get(1).equals(key)) {
			return packBy(list.subList(1, list.size()), list.get(1), sameKeyList, packList);
		} else {
			packList.add(sameKeyList);
			return packBy(list.subList(1, list.size()), list.get(1), new ArrayList(), packList);
		}
	}

	/*
	 * P10 (*) Run-length encoding of a list.
	 */
	static List encode(List list) {
		return addLength(pack(list), new ArrayList());
	}

	private static List addLength(List restList, List resultList) {
		if (restList.size() == 0)
			return resultList;
		List currentList = new ArrayList();
		currentList.add(((List) restList.get(0)).size());
		currentList.add(((List) restList.get(0)).get(0));
		resultList.add(currentList);
		return addLength(restList.subList(1, restList.size()), resultList);
	}

	/*
	 * P11 (*) Modified run-length encoding.
	 */
	static List encodeModified(List list) {
		return addObject(encode(list), new ArrayList());
	}

	private static List addObject(List list, List result) {
		if (list.size() == 0)
			return result;
		result.add(((List) list.get(0)).get(0).equals(1) ? ((List) list.get(0)).get(1) : ((List) list.get(0)));
		return addObject(list.subList(1, list.size()), result);
	}

	/*
	 * P12 (**) Decode a run-length encoded list.
	 */
	static List decode(List list) {
		return decodeList(list, new ArrayList());
	}

	private static List decodeList(List list, List result) {
		if (list.size() == 0)
			return result;
		result.add(extendList((int) ((List) list.get(0)).get(0), ((List) list.get(0)).get(1), new ArrayList()));
		return decodeList(list.subList(1, list.size()), result);
	}

	private static List extendList(int n, Object o, List result) {
		if (n == 0)
			return result;
		result.add(o);
		return extendList(n - 1, o, result);
	}
}
