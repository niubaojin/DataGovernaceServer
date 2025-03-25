package com.synway.datarelation.util.v3;

/**
 * @author ckw
 * 尤其注意：最好提供toString()实现。例如：
 * 
 * <pre>
 * 
 * &#064;Override
 * public String toString() {
 * 	return String.format(&quot;Code:[%s], Description:[%s]. &quot;, this.code, this.describe);
 * }
 * </pre>
 * 
 */
public interface ErrorCode {
	
	/**
	 * 获取错误代码
	 * @author Chen KaiWei
	 * @date 2020/10/21 13:37
	 * @param 
	 * @return {@link String}
	 * @throws
	 */
	String getCode();

	/**
	 * 错误代码描述
	 * @author Chen KaiWei
	 * @date 2020/10/21 13:39
	 * @param 
	 * @return {@link String}
	 * @throws
	 */
	String getDescription();

	/** 必须提供toString的实现
	 * <pre>
	 * &#064;Override
	 * public String toString() {
	 * 	return String.format(&quot;Code:[%s], Description:[%s]. &quot;, this.code, this.describe);
	 * }
	 * </pre>
	 */
	@Override
	String toString();
}
