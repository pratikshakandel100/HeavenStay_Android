package com.pratiksha.heavenstay.features.auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratiksha.heavenstay.R
import com.pratiksha.heavenstay.ui.theme.HeavenStayTheme

@Composable
fun RegisterScreenRoot() {
    RegisterScreen()
}


@Composable
private fun RegisterScreen() {
    // Form state
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf(UserType.User) }
    var agreeToTerms by remember { mutableStateOf(false) }
    var subscribeNewsletter by remember { mutableStateOf(false) }

    // UI state
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var errors by remember { mutableStateOf(mapOf<String, String>()) }
    val passwordStrength = calculatePasswordStrength(password)

    val focusManager = LocalFocusManager.current

    // Gradient colors from React example
    val gradientColors = listOf(Color(0xFF97B067), Color(0xFFE3DE61))
    
    val onBack:()-> Unit = {}

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3DE61).copy(alpha = 0.2f),
                        Color(0xFF97B067).copy(alpha = 0.2f)
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                Column {
                    // Header
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    colors = gradientColors
                                )
                            )
                            .padding(24.dp)
                    ) {
                        if (onBack != null) {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier.align(Alignment.TopStart)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "HeavenStay Nepal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Join Our Community",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Create your account and start exploring",
                                color = Color(0xFFBCE2B6),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Form content
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // User Type Selection
                        Text(
                            "Register as",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color(0xFF2F5249)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            UserType.values().forEach { type ->
                                val isSelected = userType == type
                                val borderColor =
                                    if (isSelected) Color(0xFF97B067) else Color.Gray.copy(alpha = 0.3f)
                                val backgroundColor =
                                    if (isSelected) Color(0x1997B067) else Color.Transparent
                                val iconTint =
                                    if (isSelected) type.color else Color.Gray.copy(alpha = 0.4f)
                                val textColor = if (isSelected) Color(0xFF2F5249) else Color.Gray

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(backgroundColor)
                                        .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                                        .clickable { userType = type }
                                        .padding(vertical = 12.dp)
                                ) {
                                    Icon(
                                        imageVector = type.icon,
                                        contentDescription = type.label,
                                        tint = iconTint,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = type.label,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor
                                    )
                                }
                            }
                        }

                        // Name fields
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // First Name
                            OutlinedTextFieldWithIcon(
                                value = firstName,
                                onValueChange = {
                                    firstName = it
                                    if (errors.containsKey("firstName")) {
                                        errors = errors - "firstName"
                                    }
                                },
                                label = "First Name",
                                placeholder = "John",
                                leadingIcon = Icons.Default.Person,
                                isError = errors.containsKey("firstName"),
                                errorMessage = errors["firstName"],
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)
                            )

                            // Last Name
                            OutlinedTextFieldWithIcon(
                                value = lastName,
                                onValueChange = {
                                    lastName = it
                                    if (errors.containsKey("lastName")) {
                                        errors = errors - "lastName"
                                    }
                                },
                                label = "Last Name",
                                placeholder = "Doe",
                                leadingIcon = Icons.Default.Person,
                                isError = errors.containsKey("lastName"),
                                errorMessage = errors["lastName"],
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)
                            )
                        }

                        // Email
                        OutlinedTextFieldWithIcon(
                            value = email,
                            onValueChange = {
                                email = it
                                if (errors.containsKey("email")) {
                                    errors = errors - "email"
                                }
                            },
                            label = "Email Address",
                            placeholder = "john@example.com",
                            leadingIcon = Icons.Default.Email,
                            isError = errors.containsKey("email"),
                            errorMessage = errors["email"],
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        // Phone
                        OutlinedTextFieldWithIcon(
                            value = phone,
                            onValueChange = {
                                phone = it
                                if (errors.containsKey("phone")) {
                                    errors = errors - "phone"
                                }
                            },
                            label = "Phone Number",
                            placeholder = "+977-9841234567",
                            leadingIcon = Icons.Default.Phone,
                            isError = errors.containsKey("phone"),
                            errorMessage = errors["phone"],
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )

                        // Password
                        PasswordTextField(
                            label = "Password",
                            password = password,
                            onPasswordChange = {
                                password = it
                                if (errors.containsKey("password")) {
                                    errors = errors - "password"
                                }
                            },
                            showPassword = showPassword,
                            onToggleShowPassword = { showPassword = !showPassword },
                            isError = errors.containsKey("password"),
                            errorMessage = errors["password"],
                            placeholder = "Create a strong password"
                        )

                        // Password strength indicator
                        if (password.isNotEmpty()) {
                            PasswordStrengthIndicator(strength = passwordStrength)
                        }

                        // Confirm Password
                        PasswordTextField(
                            label = "Confirm Password",
                            password = confirmPassword,
                            onPasswordChange = {
                                confirmPassword = it
                                if (errors.containsKey("confirmPassword")) {
                                    errors = errors - "confirmPassword"
                                }
                            },
                            showPassword = showConfirmPassword,
                            onToggleShowPassword = { showConfirmPassword = !showConfirmPassword },
                            isError = errors.containsKey("confirmPassword"),
                            errorMessage = errors["confirmPassword"],
                            placeholder = "Confirm your password"
                        )

                        // Checkboxes
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = agreeToTerms,
                                    onCheckedChange = {
                                        agreeToTerms = it
                                        if (errors.containsKey("agreeToTerms")) {
                                            errors = errors - "agreeToTerms"
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF97B067))
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "I agree to the Terms and Conditions and Privacy Policy",
                                    fontSize = 14.sp,
                                    color = Color(0xFF2F5249),
                                    modifier = Modifier.clickable { agreeToTerms = !agreeToTerms }
                                )
                            }
                            if (errors.containsKey("agreeToTerms")) {
                                Text(
                                    text = errors["agreeToTerms"] ?: "",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = subscribeNewsletter,
                                    onCheckedChange = { subscribeNewsletter = it },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF97B067))
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Subscribe to our newsletter for travel tips and exclusive offers",
                                    fontSize = 14.sp,
                                    color = Color(0xFF2F5249),
                                    modifier = Modifier.clickable {
                                        subscribeNewsletter = !subscribeNewsletter
                                    }
                                )
                            }
                        }

                        // Submit button
                        Button(
                            onClick = {
                                val validationErrors = validateForm(
                                    firstName,
                                    lastName,
                                    email,
                                    phone,
                                    password,
                                    confirmPassword,
                                    agreeToTerms
                                )
                                errors = validationErrors
                                if (validationErrors.isEmpty()) {
                                    // Handle successful signup here
                                    println("Signup data: $firstName $lastName $email $phone $password $userType $agreeToTerms $subscribeNewsletter")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(Color.Transparent),

                            contentPadding = PaddingValues()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = gradientColors
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Create Account",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        // Divider with "or"
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Divider(
                                modifier = Modifier.weight(1f),
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                            Text(
                                "or",
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Divider(
                                modifier = Modifier.weight(1f),
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                        }

                        // Social signup buttons
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = { /* TODO: Google signup */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray.copy(alpha = 0.3f)
                                    )
                                ,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFF2F5249)
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Add your google logo drawable
                                    contentDescription = "Google",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Sign up with Google")
                            }

                            Button(
                                onClick = { /* TODO: Facebook signup */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1877F2))
                                ,
                            ) {
                                Text("Sign up with Facebook", style = TextStyle(
                                    color = Color.White
                                ))
                            }
                        }

                        // Login link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Already have an account? ",
                                color = Color(0xFF2F5249),
                                fontSize = 14.sp
                            )
                            Text(
                                "Sign in here",
                                color = Color(0xFF437057),
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                modifier = Modifier.clickable {
                                    // TODO: Navigate to login screen
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
enum class UserType(val label: String, val icon: ImageVector, val color: Color) {
    User("Guest/User", Icons.Default.Person, Color(0xFF437057)),
    HotelOwner("Hotel Owner", Icons.Default.AccountBox, Color(0xFF97B067)),
    Admin("Admin", Icons.Default.Lock, Color(0xFFE3DE61))
}

@Composable
fun OutlinedTextFieldWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    isError: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color(0xFF2F5249)) },
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color(0xFF437057)
                )
            },
            isError = isError,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,

            modifier = Modifier.fillMaxWidth()
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordTextField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onToggleShowPassword: () -> Unit,
    isError: Boolean,
    errorMessage: String?,
    placeholder: String
) {
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(label, color = Color(0xFF2F5249)) },
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFF437057)
                )
            },
            trailingIcon = {
                IconButton(onClick = onToggleShowPassword) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.Close else Icons.Outlined.Lock,
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = Color(0xFF437057)
                    )
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isError,
            singleLine = true,

            modifier = Modifier.fillMaxWidth()
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordStrengthIndicator(strength: Int) {
    val color = when {
        strength < 2 -> Color(0xFFEF4444) // Red
        strength < 4 -> Color(0xFFE3DE61) // Yellow-ish
        else -> Color(0xFF97B067) // Green
    }
    val text = when {
        strength < 2 -> "Weak"
        strength < 4 -> "Medium"
        else -> "Strong"
    }

    Column {
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth((strength / 5f).coerceIn(0f, 1f))
                        .background(color)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

fun calculatePasswordStrength(password: String): Int {
    var strength = 0
    if (password.length >= 8) strength++
    if (password.any { it.isUpperCase() }) strength++
    if (password.any { it.isLowerCase() }) strength++
    if (password.any { it.isDigit() }) strength++
    if (password.any { !it.isLetterOrDigit() }) strength++
    return strength
}

fun validateForm(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    password: String,
    confirmPassword: String,
    agreeToTerms: Boolean
): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    if (firstName.trim().isEmpty()) {
        errors["firstName"] = "First name is required"
    }
    if (lastName.trim().isEmpty()) {
        errors["lastName"] = "Last name is required"
    }
    if (email.isEmpty()) {
        errors["email"] = "Email is required"
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        errors["email"] = "Please enter a valid email"
    }
    if (phone.isEmpty()) {
        errors["phone"] = "Phone number is required"
    } else if (!phone.matches(Regex("^\\+?[\\d\\s\\-()]{10,}\$"))) {
        errors["phone"] = "Please enter a valid phone number"
    }
    if (password.isEmpty()) {
        errors["password"] = "Password is required"
    } else if (password.length < 8) {
        errors["password"] = "Password must be at least 8 characters"
    }
    if (confirmPassword.isEmpty()) {
        errors["confirmPassword"] = "Please confirm your password"
    } else if (password != confirmPassword) {
        errors["confirmPassword"] = "Passwords do not match"
    }
    if (!agreeToTerms) {
        errors["agreeToTerms"] = "You must agree to the terms and conditions"
    }

    return errors
}


@Preview
@Composable
private fun RegisterScreenPreview() {
    HeavenStayTheme {
        RegisterScreen()
    }
}