const API_BASE_URL = "/api";

const outputContainer = document.getElementById("outputContainer");
const outputContent = document.getElementById("outputContent");

function displayResponse(data) {
    outputContainer.classList.remove("hidden");
    outputContent.textContent = JSON.stringify(data, null, 2);
    outputContainer.scrollIntoView({ behavior: 'smooth' });
}

// 1. CREATE ACCOUNT
document.getElementById("createAccountForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
        accountNumber: parseInt(document.getElementById("createAccNum").value),
        accountHolderName: document.getElementById("createAccHolder").value,
        email: document.getElementById("createEmail").value,
        phoneNumber: document.getElementById("createPhone").value,
        accountType: document.getElementById("createAccType").value,
        initialDeposit: parseFloat(document.getElementById("createInitialDeposit").value) || 0
    };

    try {
        const res = await fetch(`${API_BASE_URL}/accounts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 2. DEPOSIT
document.getElementById("depositForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const accNum = document.getElementById("depositAccNum").value;
    const amount = parseFloat(document.getElementById("depositAmount").value);

    try {
        const res = await fetch(`${API_BASE_URL}/accounts/${accNum}/deposit`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ amount })
        });
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 3. WITHDRAW
document.getElementById("withdrawForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const accNum = document.getElementById("withdrawAccNum").value;
    const amount = parseFloat(document.getElementById("withdrawAmount").value);

    try {
        const res = await fetch(`${API_BASE_URL}/accounts/${accNum}/withdraw`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ amount })
        });
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 4. CHECK BALANCE
document.getElementById("checkBalanceForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const accNum = document.getElementById("balanceAccNum").value;

    try {
        const res = await fetch(`${API_BASE_URL}/accounts/${accNum}/balance`);
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 5. ACCOUNT DETAILS
document.getElementById("accDetailsForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const accNum = document.getElementById("detailsAccNum").value;

    try {
        const res = await fetch(`${API_BASE_URL}/accounts/${accNum}`);
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 6. TRANSACTION HISTORY
document.getElementById("txHistoryForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const accNum = document.getElementById("txAccNum").value;

    try {
        const res = await fetch(`${API_BASE_URL}/accounts/${accNum}/transactions`);
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});

// 7. ALL ACCOUNTS
document.getElementById("btnViewAllAccounts").addEventListener("click", async () => {
    try {
        const res = await fetch(`${API_BASE_URL}/accounts`);
        const data = await res.json();
        displayResponse(data);
    } catch (err) {
        displayResponse({ success: false, message: err.message });
    }
});
