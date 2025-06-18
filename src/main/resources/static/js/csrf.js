document.addEventListener('DOMContentLoaded', function() {
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;

    // Для Fetch API
    const originalFetch = window.fetch;
    window.fetch = function(url, options = {}) {
        options.headers = options.headers || {};
        if (['POST', 'PUT', 'PATCH', 'DELETE'].includes(options.method)) {
            options.headers[header] = token;
        }
        return originalFetch(url, options);
    };

    // Для XMLHttpRequest
    const originalOpen = XMLHttpRequest.prototype.open;
    XMLHttpRequest.prototype.open = function(method, url) {
        originalOpen.apply(this, arguments);
        if (['POST', 'PUT', 'PATCH', 'DELETE'].includes(method.toUpperCase())) {
            this.setRequestHeader(header, token);
        }
    };
});