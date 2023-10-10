export  const deepClone = (obj) => {
    if (typeof obj !== 'object' || obj === null) {
        return obj;
    }

    if (Array.isArray(obj)) {
        const clonedArray = [];
        for (let i = 0; i < obj.length; i++) {
            clonedArray[i] = deepClone(obj[i]);
        }
        return clonedArray;
    }

    const clonedObject = {};
    for (const key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) { // 修复这里
            clonedObject[key] = deepClone(obj[key]);
        }
    }

    return clonedObject;
}