
const { ipcRenderer: ipc } = (window.require && window.require('electron')) || window.electron || {}

/**
 * 发送异步消息（invoke/handle 模型）
 * @param channel
 * @param param
 * @returns {Promise}
 */
export function invoke(channel: any, param:any) {
	const message = ipc.invoke(channel, param);
  return message;
}

/**
 * 发送同步消息（send/on 模型）
 * @param channel
 * @param param
 * @returns {Any}
 */
 export function sendSync(channel: any, param:any) {
	const message = ipc.sendSync(channel, param);
  return message;
}

const IpcRenderer = {
	invoke: (channel: any, param:any) => {
		return invoke(channel, param);
	},
	sendSync: (channel: any, param:any) => {
		return sendSync(channel, param);
	},
	
};

// 统一批量导出
export default IpcRenderer;


